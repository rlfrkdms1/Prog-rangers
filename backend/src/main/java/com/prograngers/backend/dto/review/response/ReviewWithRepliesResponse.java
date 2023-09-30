package com.prograngers.backend.dto.review.response;

<<<<<<< HEAD:backend/src/main/java/com/prograngers/backend/dto/review/response/SolutionReview.java
import com.prograngers.backend.entity.Review;
=======

import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.review.ReviewStatusConstant;

>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1:backend/src/main/java/com/prograngers/backend/dto/review/response/ReviewWithRepliesResponse.java
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
<<<<<<< HEAD:backend/src/main/java/com/prograngers/backend/dto/review/response/SolutionReview.java
public class SolutionReview {
=======
public class ReviewWithRepliesResponse {

>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1:backend/src/main/java/com/prograngers/backend/dto/review/response/ReviewWithRepliesResponse.java
    private Long id;
    private String nickname;
    private String photo;
    private String content;
    private List<ReplyResponse> replies;
    private boolean mine;
<<<<<<< HEAD:backend/src/main/java/com/prograngers/backend/dto/review/response/SolutionReview.java
    public static SolutionReview from(Review review, Long memberId){
=======

    private ReviewStatusConstant status;
    public static ReviewWithRepliesResponse from(Review review, boolean reviewIsMine){

>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1:backend/src/main/java/com/prograngers/backend/dto/review/response/ReviewWithRepliesResponse.java

        ReviewWithRepliesResponse reviewResponseWithRepliesResponse = ReviewWithRepliesResponse.builder()
                .id(review.getId())
                .nickname(review.getMember().getNickname())
                .photo(review.getMember().getPhoto())
                .content(review.getContent())
                .replies(new ArrayList<>())
                .mine(checkReviewIsMine(review,memberId))
                .build();
        return reviewResponseWithRepliesResponse;
    }

    private static boolean checkReviewIsMine(Review review, Long memberId) {
        if (review.getMember().getId().equals(memberId)) return true;
        return false;
    }
}
