package com.prograngers.backend.dto.review.response;

<<<<<<< HEAD:backend/src/main/java/com/prograngers/backend/dto/review/response/SolutionReviewReply.java
import com.prograngers.backend.entity.Review;
=======
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.review.ReviewStatusConstant;
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1:backend/src/main/java/com/prograngers/backend/dto/review/response/ReplyResponse.java
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Builder
public class ReplyResponse {
    private Long id;
    private String nickname;
    private String photo;
    private String content;

<<<<<<< HEAD:backend/src/main/java/com/prograngers/backend/dto/review/response/SolutionReviewReply.java
    private boolean mine;

    public static SolutionReviewReply from(Review review, Long memberId) {
        SolutionReviewReply solutionReviewReply = SolutionReviewReply.builder()
=======
    private ReviewStatusConstant status;


    private boolean mine;

    public static ReplyResponse from(Review review, boolean reviewIsMine) {

        ReplyResponse replyResponse = ReplyResponse.builder()
                .status(review.getStatus())
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1:backend/src/main/java/com/prograngers/backend/dto/review/response/ReplyResponse.java
                .id(review.getId())
                .nickname(review.getMember().getNickname())
                .photo(review.getMember().getPhoto())
                .content(review.getContent())
                .mine(checkReviewIsMine(review,memberId))
                .build();
        return replyResponse;
    }

    private static boolean checkReviewIsMine(Review review, Long memberId) {
        if (review.getMember().getId().equals(memberId)) return true;
        return false;
    }
}
