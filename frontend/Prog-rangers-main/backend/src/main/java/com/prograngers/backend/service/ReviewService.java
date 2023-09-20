package com.prograngers.backend.service;

import com.prograngers.backend.dto.review.response.Line;
import com.prograngers.backend.dto.review.response.Reply;
import com.prograngers.backend.dto.review.response.Review;
import com.prograngers.backend.dto.review.response.SolutionReviewsResponse;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.repository.review.ReviewRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final SolutionRepository solutionRepository;

    public SolutionReviewsResponse getReviewDetail(Long solutionId) {

        // solutionId에 해당하는 풀이 찾기
        Solution solution = solutionRepository.findById(solutionId).orElseThrow(SolutionNotFoundException::new);

        // 줄 나눠서 배열에 저장
        String[] lines = solution.getCode().split("\n");

        // 최종 응답 dto에 풀이 내용을 넣는다
        SolutionReviewsResponse solutionReviewsResponse = SolutionReviewsResponse.from(solution, lines);

        // 최종 응답 dto에서 line들을 가져온다
        List<Line> addedLines = solutionReviewsResponse.getLines();

        // 라인들에 대해 for문을 돌면서 리뷰를 추가한다
        for (Line line : addedLines) {
            Integer codeLineNumber = line.getCodeLineNumber();

            // codeLineNumber에 해당하는 review들을 찾는다
            List<com.prograngers.backend.entity.Review> reviews = reviewRepository
                    .findAllByCodeLineNumberOrderByCreatedDateAsc(codeLineNumber);
            log.info("코드라인 : {}", codeLineNumber);
            List<Review> reviewRespons = new ArrayList<>();

            // 해당 라인의 리뷰들에 대해 for문을 돈다
            for (com.prograngers.backend.entity.Review review : reviews) {
                log.info("리뷰 id {}, 리뷰 내용 : {}, 부모 리뷰 id : {}", review.getId(), review.getContent(), review.getParentId());

                // 부모가 없는 리뷰인 경우 ReviewResponse dto로 만든다
                if (review.getParentId() == null) {
                    reviewRespons.add(Review.from(review));
                }

                // 부모가 있는 리뷰인 경우 Replyresponse dto로 만든다
                else {
                    for (Review r : reviewRespons) {
                        if (r.getId().equals(review.getParentId())) {
                            r.getReplies().add(Reply.from(review));
                        }
                    }
                }
            }
            line.setReviews(reviewRespons);
        }
        solutionReviewsResponse.setLines(addedLines);
        return solutionReviewsResponse;
    }

}
