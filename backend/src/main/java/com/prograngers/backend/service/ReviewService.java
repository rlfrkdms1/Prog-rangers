package com.prograngers.backend.service;

import static com.prograngers.backend.entity.review.ReviewStatusConstant.DELETED;

import com.prograngers.backend.dto.review.request.UpdateReviewRequest;
import com.prograngers.backend.dto.review.request.WriteReviewRequest;
import com.prograngers.backend.dto.review.response.CodeLineWithReview;
import com.prograngers.backend.dto.review.response.ReplyResponse;
import com.prograngers.backend.dto.review.response.ReviewWithRepliesResponse;
import com.prograngers.backend.dto.review.response.ShowReviewsResponse;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.badrequest.DifferentCodeLineNumberException;
import com.prograngers.backend.exception.badrequest.DifferentSolutionException;
import com.prograngers.backend.exception.badrequest.InvalidCodeLIneNumberException;
import com.prograngers.backend.exception.badrequest.InvalidParentException;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.exception.notfound.ReviewAlreadyDeletedException;
import com.prograngers.backend.exception.notfound.ReviewNotFoundException;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.exception.unauthorization.MemberUnAuthorizedException;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.review.ReviewRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final SolutionRepository solutionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void writeReview(WriteReviewRequest writeReviewRequest, Long memberId, Long solutionId) {
        Member writer = findMemberById(memberId);
        Solution solution = findSolutionById(solutionId);
        validCodeLineNumber(writeReviewRequest, solution.getCode().split("\n").length);
        if (writeReviewRequest.getParentId() != null) {
            validParentExists(writeReviewRequest);
            validSameSolution(writeReviewRequest, solutionId);
            validSameCodeLineWithParent(writeReviewRequest);
        }
        reviewRepository.save(writeReviewRequest.toReview(writer, solution));
    }

    private void validSameCodeLineWithParent(WriteReviewRequest writeReviewRequest) {
        if (reviewRepository.findById(writeReviewRequest.getParentId()).get().getCodeLineNumber()
                != writeReviewRequest.getCodeLineNumber()) {
            throw new DifferentCodeLineNumberException();
        }
    }

    private void validCodeLineNumber(WriteReviewRequest writeReviewRequest, int length) {
        if (writeReviewRequest.getCodeLineNumber() > length || writeReviewRequest.getCodeLineNumber() <= 0) {
            throw new InvalidCodeLIneNumberException();
        }
    }

    private void validSameSolution(WriteReviewRequest writeReviewRequest, Long solutionId) {
        if (solutionId != reviewRepository.findById(writeReviewRequest.getParentId())
                .get()
                .getSolution()
                .getId()) {
            throw new DifferentSolutionException();
        }
    }

    private void validParentExists(WriteReviewRequest writeReviewRequest) {
        if (!reviewRepository.existsById(writeReviewRequest.getParentId())) {
            throw new InvalidParentException();
        }
    }

    @Transactional
    public void updateReview(UpdateReviewRequest updateReviewRequest, Long memberId, Long reviewId) {
        Review review = findReviewById(reviewId);
        Member member = findMemberById(memberId);
        validMemberAuthorization(review, member);
        validReviewAlreadyDeleted(review);
        review.update(updateReviewRequest.getContent());
    }

    @Transactional
    public void deleteReview(Long memberId, Long reviewId) {
        Review review = findReviewById(reviewId);
        Member member = findMemberById(memberId);
        validMemberAuthorization(review, member);
        validReviewAlreadyDeleted(review);
        review.delete();
    }

    public ShowReviewsResponse getReviewDetail(Long solutionId, Long memberId) {
        // solutionId에 해당하는 풀이 찾기
        Solution solution = findSolutionById(solutionId);
        // 줄 나눠서 배열에 저장
        String[] lines = solution.getCode().split("\n");
        // 최종 응답 dto에 풀이 내용을 넣는다
        ShowReviewsResponse showReviewsResponse = ShowReviewsResponse.from(solution, lines);
        // 최종 응답 dto에서 line들을 가져온다
        List<CodeLineWithReview> addedCodeLineResponsWithReviews = showReviewsResponse.getCodeLineWithReview();
        addReviewAtLine(addedCodeLineResponsWithReviews, memberId);
        showReviewsResponse.setCodeLineWithReview(addedCodeLineResponsWithReviews);
        return showReviewsResponse;
    }

    private Solution findSolutionById(Long solutionId) {
        return solutionRepository.findById(solutionId).orElseThrow(SolutionNotFoundException::new);
    }

    private void addReviewAtLine(List<CodeLineWithReview> addedCodeLineResponsWithReviews, Long memberId) {
        // 라인들에 대해 for문을 돌면서 리뷰를 추가한다
        for (CodeLineWithReview codeLineWithReview : addedCodeLineResponsWithReviews) {
            Integer codeLineNumber = codeLineWithReview.getCodeLineNumber();

            // codeLineNumber에 해당하는 review들을 찾는다
            List<Review> reviews = reviewRepository
                    .findAllByCodeLineNumberOrderByCreatedAtAsc(codeLineNumber);
            List<ReviewWithRepliesResponse> reviewResponseWithRepliesResponse = new ArrayList<>();

            // 해당 라인의 리뷰들에 대해 for문을 돈다
            for (Review review : reviews) {
                // 부모가 없는 리뷰인 경우 ReviewWithRepliesResponse dto로 만든다
                if (review.getParentId() == null) {
                    makeReviewResponse(reviewResponseWithRepliesResponse, review, memberId);
                }
                // 부모가 있는 리뷰인 경우 Replyresponse dto로 만든다
                else {
                    makeReplyResponse(reviewResponseWithRepliesResponse, review, memberId);
                }
            }
            codeLineWithReview.setReviews(reviewResponseWithRepliesResponse);
        }
    }

    private void makeReviewResponse(List<ReviewWithRepliesResponse> reviewResponseWithRepliesResponse, Review review,
                                    Long memberId) {
        reviewResponseWithRepliesResponse.add(
                ReviewWithRepliesResponse.from(review, validReviewIsMine(review, memberId)));
    }

    private void makeReplyResponse(List<ReviewWithRepliesResponse> reviewResponseWithRepliesResponse, Review review,
                                   Long memberId) {
        for (ReviewWithRepliesResponse r : reviewResponseWithRepliesResponse) {
            if (r.getId().equals(review.getParentId())) {
                r.getReplies().add(ReplyResponse.from(review, validReviewIsMine(review, memberId)));
            }
        }
    }

    private boolean validReviewIsMine(Review review, Long memberId) {
        if (review.getMember().getId().equals(memberId)) {
            return true;
        }
        return false;
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    private Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
    }

    private void validMemberAuthorization(Review targetReview, Member member) {
        if (!targetReview.getMember().getId().equals(member.getId())) {
            throw new MemberUnAuthorizedException();
        }
    }

    private void validReviewAlreadyDeleted(Review targetReview) {
        if (targetReview.getStatus().equals(DELETED)) {
            throw new ReviewAlreadyDeletedException();
        }
    }

}
