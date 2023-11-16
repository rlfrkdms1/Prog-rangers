package com.prograngers.backend.dto.review.response;


import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.review.ReviewStatusConstant;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReviewWithRepliesResponse {

    private Long id;
    private String nickname;
    private String photo;
    private String content;
    private List<ReplyResponse> replies;
    private boolean mine;

    private ReviewStatusConstant status;

    public static ReviewWithRepliesResponse from(Review review, boolean reviewIsMine) {

        ReviewWithRepliesResponse reviewResponseWithRepliesResponse = ReviewWithRepliesResponse.builder()
                .id(review.getId())
                .nickname(review.getMember().getNickname())
                .photo(review.getMember().getPhoto())
                .content(review.getContent())
                .status(review.getStatus())
                .replies(new ArrayList<>())
                .mine(reviewIsMine)
                .build();
        return reviewResponseWithRepliesResponse;
    }

}
