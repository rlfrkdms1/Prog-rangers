package com.prograngers.backend.dto.review.response;


import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.review.ReviewStatusConStant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class SolutionReview {

    private Long id;
    private String nickname;
    private String photo;
    private String content;
    private List<SolutionReviewReply> replies;
    private boolean mine;

    private ReviewStatusConStant status;
    public static SolutionReview from(Review review, boolean reviewIsMine){


        SolutionReview solutionReviewResponse = SolutionReview.builder()
                .id(review.getId())
                .nickname(review.getMember().getNickname())
                .photo(review.getMember().getPhoto())
                .content(review.getContent())
                .status(review.getStatus())
                .replies(new ArrayList<>())
                .mine(reviewIsMine)
                .build();
        return solutionReviewResponse;
    }

}
