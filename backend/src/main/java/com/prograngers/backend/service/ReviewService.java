package com.prograngers.backend.service;

import com.prograngers.backend.dto.review.response.SolutionLine;
import com.prograngers.backend.dto.review.response.SolutionReviewReply;
import com.prograngers.backend.dto.review.response.SolutionReview;
import com.prograngers.backend.dto.review.response.SolutionReviewsResponse;
import com.prograngers.backend.entity.Review;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.repository.review.ReviewRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final SolutionRepository solutionRepository;

    public SolutionReviewsResponse getReviewDetail(Long solutionId,Long memberId) {
        // solutionId에 해당하는 풀이 찾기
        Solution solution = solutionRepository.findById(solutionId).orElseThrow(SolutionNotFoundException::new);
        // 줄 나눠서 배열에 저장
        String[] lines = solution.getCode().split("\n");
        // 최종 응답 dto에 풀이 내용을 넣는다
        SolutionReviewsResponse solutionReviewsResponse = SolutionReviewsResponse.from(solution, lines);
        // 최종 응답 dto에서 line들을 가져온다
        List<SolutionLine> addedSolutionLines = solutionReviewsResponse.getSolutionLines();
        addReviewAtLine(solution, addedSolutionLines, memberId);
        solutionReviewsResponse.setSolutionLines(addedSolutionLines);
        return solutionReviewsResponse;
    }

    private void addReviewAtLine(Solution solution, List<SolutionLine> addedSolutionLines,Long memberId) {
        // 라인들에 대해 for문을 돌면서 리뷰를 추가한다
        addedSolutionLines.stream()
                .forEach(solutionLine -> {
                    Integer codeLineNumber = solutionLine.getCodeLineNumber();


                    // codeLineNumber에 해당하는 review들을 찾는다
                    List<Review> reviews = reviewRepository
                            .findAllByCodeLineNumberOrderByCreatedAtAsc(codeLineNumber);

                    List<SolutionReview> solutionReviewResponse =
                            reviews.stream()
                                    .filter(review -> review.getParentId() == null)
                                    .map(review -> SolutionReview.from(review, memberId))
                                    .collect(Collectors.toList());

                    // 부모가 있는 리뷰들 (답 리뷰들)
                    reviews.stream().filter(review -> review.getParentId() != null)
                            .forEach(review -> makeReplyResponse(solutionReviewResponse, review));

                    solutionLine.setSolutionReviews(solutionReviewResponse);
                });
    }

    private static void makeReplyResponse(List<SolutionReview> solutionReviewResponse, Review review) {
        solutionReviewResponse.stream()
                .filter(parentReview->parentReview.getId().equals(review.getParentId()))
                .findFirst()
                .get()
                .getReplies()
                .add(SolutionReviewReply.from(review));
    }
}
