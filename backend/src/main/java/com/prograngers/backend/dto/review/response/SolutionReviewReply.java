package com.prograngers.backend.dto.review.response;

import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.review.ReviewStatusConStant;
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
public class SolutionReviewReply {
    private Long id;
    private String nickname;
    private String photo;
    private String content;


    private boolean mine;

    public static SolutionReviewReply from(Review review, Long memberId) {

        SolutionReviewReply solutionReviewReply = SolutionReviewReply.builder()
                .status(review.getStatus())
                .id(review.getId())
                .nickname(review.getMember().getNickname())
                .photo(review.getMember().getPhoto())
                .content(review.getContent())
                .mine(checkReviewIsMine(review,memberId))
                .build();
        return solutionReviewReply;
    }

    private static boolean checkReviewIsMine(Review review, Long memberId) {
        if (review.getMember().getId().equals(memberId)) return true;
        return false;
    }
}
