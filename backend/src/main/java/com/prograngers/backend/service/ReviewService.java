package com.prograngers.backend.service;

import com.prograngers.backend.dto.review.request.ReviewPatchRequest;
import com.prograngers.backend.dto.review.request.ReviewPostRequest;
import com.prograngers.backend.dto.review.response.SolutionLine;
import com.prograngers.backend.dto.review.response.SolutionReviewReply;
import com.prograngers.backend.dto.review.response.SolutionReview;
import com.prograngers.backend.dto.review.response.SolutionReviewsResponse;
import com.prograngers.backend.entity.Review;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.exception.notfound.ReviewNotFoundException;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.exception.unauthorization.MemberUnAuthorizedException;
import com.prograngers.backend.exception.unauthorization.UnAuthorizationException;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.review.ReviewRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final SolutionRepository solutionRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public void writeReview(ReviewPostRequest reviewPostRequest, Long memberId,Long solutionId) {
        Member writer = findMemberById(memberId);
        Solution solution = findSolutionById(solutionId);
        reviewRepository.save(reviewPostRequest.toReview(writer,solution));
    }
    @Transactional
    public void updateReview(ReviewPatchRequest reviewPatchRequest, Long memberId, Long reviewId) {
        Review targetReview = findReviewById(reviewId);
        Member member = findMemberById(memberId);
        checkMemberAuthorization(targetReview, member);
        reviewPatchRequest.updateReview(targetReview);
    }

    public SolutionReviewsResponse getReviewDetail(Long solutionId) {
        // solutionId에 해당하는 풀이 찾기
        Solution solution = findSolutionById(solutionId);
        // 줄 나눠서 배열에 저장
        String[] lines = solution.getCode().split("\n");
        // 최종 응답 dto에 풀이 내용을 넣는다
        SolutionReviewsResponse solutionReviewsResponse = SolutionReviewsResponse.from(solution, lines);
        // 최종 응답 dto에서 line들을 가져온다
        List<SolutionLine> addedSolutionLines = solutionReviewsResponse.getSolutionLines();
        addReviewAtLine(addedSolutionLines);
        solutionReviewsResponse.setSolutionLines(addedSolutionLines);
        return solutionReviewsResponse;
    }

    private Solution findSolutionById(Long solutionId) {
        Solution solution = solutionRepository.findById(solutionId).orElseThrow(SolutionNotFoundException::new);
        return solution;
    }

    private void addReviewAtLine(List<SolutionLine> addedSolutionLines) {
        // 라인들에 대해 for문을 돌면서 리뷰를 추가한다
        for (SolutionLine solutionLine : addedSolutionLines) {
            Integer codeLineNumber = solutionLine.getCodeLineNumber();

            // codeLineNumber에 해당하는 review들을 찾는다
            List<com.prograngers.backend.entity.Review> reviews = reviewRepository
                    .findAllByCodeLineNumberOrderByCreatedAtAsc(codeLineNumber);
            List<SolutionReview> solutionReviewResponse = new ArrayList<>();

            // 해당 라인의 리뷰들에 대해 for문을 돈다
            for (Review review : reviews) {
                // 부모가 없는 리뷰인 경우 ReviewResponse dto로 만든다
                if (review.getParentId() == null) {
                    makeReviewResponse(solutionReviewResponse, review);
                }
                // 부모가 있는 리뷰인 경우 Replyresponse dto로 만든다
                else {
                    makeReplyResponse(solutionReviewResponse, review);
                }
            }
            solutionLine.setSolutionReviews(solutionReviewResponse);
        }
    }

    private static void makeReviewResponse(List<SolutionReview> solutionReviewResponse, Review review) {
        solutionReviewResponse.add(SolutionReview.from(review));
    }

    private static void makeReplyResponse(List<SolutionReview> solutionReviewResponse, Review review) {
        for (SolutionReview r : solutionReviewResponse) {
            if (r.getId().equals(review.getParentId())) {
                r.getReplies().add(SolutionReviewReply.from(review));
            }
        }
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    private Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
    }

    private static void checkMemberAuthorization(Review targetReview, Member member) {
        if (!targetReview.getMember().getId().equals(member.getId())){
            throw new MemberUnAuthorizedException();
        }
    }
}
