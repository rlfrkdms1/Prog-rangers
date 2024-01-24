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
public class ReplyResponse {
    private Long id;
    private String nickname;
    private String photo;
    private String content;
    private boolean updated;
    private boolean mine;

    public static ReplyResponse from(Review review, boolean reviewIsMine) {

        ReplyResponse replyResponse = ReplyResponse.builder()
                .updated(review.isUpdated())
                .id(review.getId())
                .nickname(review.getMember().getNickname())
                .photo(review.getMember().getPhoto())
                .content(review.getContent())
                .mine(reviewIsMine)
                .build();
        return replyResponse;
    }
}
