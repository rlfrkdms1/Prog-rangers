package com.prograngers.backend.service;

<<<<<<< HEAD
import com.prograngers.backend.dto.review.response.SolutionLine;
import com.prograngers.backend.dto.review.response.SolutionReviewReply;
import com.prograngers.backend.dto.review.response.SolutionReview;
import com.prograngers.backend.dto.review.response.SolutionReviewsResponse;
import com.prograngers.backend.entity.Review;
=======
import com.prograngers.backend.dto.review.request.UpdateReviewRequest;
import com.prograngers.backend.dto.review.request.WriteReviewRequest;
import com.prograngers.backend.dto.review.response.CodeLineWithReview;
import com.prograngers.backend.dto.review.response.ReplyResponse;
import com.prograngers.backend.dto.review.response.ReviewWithRepliesResponse;
import com.prograngers.backend.dto.review.response.ShowReviewsResponse;
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.member.Member;
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.repository.review.ReviewRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD
import java.util.stream.Collectors;
=======

import static com.prograngers.backend.entity.review.ReviewStatusConstant.*;
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final SolutionRepository solutionRepository;

<<<<<<< HEAD
    public SolutionReviewsResponse getReviewDetail(Long solutionId,Long memberId) {
=======
    private final MemberRepository memberRepository;

    @Transactional
    public void writeReview(WriteReviewRequest writeReviewRequest, Long memberId, Long solutionId) {
        Member writer = findMemberById(memberId);
        Solution solution = findSolutionById(solutionId);
        reviewRepository.save(writeReviewRequest.toReview(writer,solution));
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
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
        // solutionId에 해당하는 풀이 찾기
        Solution solution = solutionRepository.findById(solutionId).orElseThrow(SolutionNotFoundException::new);
        // 줄 나눠서 배열에 저장
        String[] lines = solution.getCode().split("\n");
        // 최종 응답 dto에 풀이 내용을 넣는다
        ShowReviewsResponse showReviewsResponse = ShowReviewsResponse.from(solution, lines);
        // 최종 응답 dto에서 line들을 가져온다
<<<<<<< HEAD
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
                            .forEach(review -> makeReplyResponse(solutionReviewResponse, review,memberId));

                    solutionLine.setSolutionReviews(solutionReviewResponse);
                });
    }

    private static void makeReplyResponse(List<SolutionReview> solutionReviewResponse, Review review,Long memberId) {
        solutionReviewResponse.stream()
                .filter(parentReview->parentReview.getId().equals(review.getParentId()))
                .findFirst()
                .get()
                .getReplies()
                .add(SolutionReviewReply.from(review,memberId));
    }
=======
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

    private void makeReviewResponse(List<ReviewWithRepliesResponse> reviewResponseWithRepliesResponse, Review review, Long memberId) {
        reviewResponseWithRepliesResponse.add(ReviewWithRepliesResponse.from(review, validReviewIsMine(review,memberId)));
    }

    private void makeReplyResponse(List<ReviewWithRepliesResponse> reviewResponseWithRepliesResponse, Review review, Long memberId) {
        for (ReviewWithRepliesResponse r : reviewResponseWithRepliesResponse) {
            if (r.getId().equals(review.getParentId())) {
                r.getReplies().add(ReplyResponse.from(review,validReviewIsMine(review,memberId)));
            }
        }
    }

    private boolean validReviewIsMine(Review review, Long memberId) {
        if (review.getMember().getId().equals(memberId)) return true;
        return false;
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    private Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
    }

    private void validMemberAuthorization(Review targetReview, Member member) {
        if (!targetReview.getMember().getId().equals(member.getId())){
            throw new MemberUnAuthorizedException();
        }
    }

    private void validReviewAlreadyDeleted(Review targetReview) {
        if (targetReview.getStatus().equals(DELETED)){
            throw new ReviewAlreadyDeletedException();
        }
    }

>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
}
