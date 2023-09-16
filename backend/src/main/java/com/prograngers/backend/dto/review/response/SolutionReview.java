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
    public static SolutionReview from(Review review, Long memberId){


        SolutionReview solutionReviewResponse = SolutionReview.builder()
                .id(review.getId())
                .nickname(review.getMember().getNickname())
                .photo(review.getMember().getPhoto())
                .content(review.getContent())
                .status(review.getStatus())
                .replies(new ArrayList<>())
                .mine(checkReviewIsMine(review,memberId))
                .build();
        return solutionReviewResponse;
    }

    private static boolean checkReviewIsMine(Review review, Long memberId) {
        if (review.getMember().getId().equals(memberId)) return true;
        return false;
    }
}
