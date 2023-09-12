package com.prograngers.backend.dto.review.response;

import com.prograngers.backend.entity.review.Review;
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

    public static SolutionReviewReply from(Review review) {
        SolutionReviewReply solutionReviewReply = SolutionReviewReply.builder()
                .id(review.getId())
                .nickname(review.getMember().getNickname())
                .photo(review.getMember().getPhoto())
                .content(review.getContent())
                .build();
        return solutionReviewReply;
    }
}
