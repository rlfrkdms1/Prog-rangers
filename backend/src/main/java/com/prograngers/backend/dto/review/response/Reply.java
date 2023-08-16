package com.prograngers.backend.dto.review.response;

import com.prograngers.backend.entity.Review;
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
public class Reply {
    private Long id;
    private String nickname;
    private String photo;
    private String content;

    public static Reply from(Review review) {
        Reply reply = Reply.builder()
                .id(review.getId())
                .nickname(review.getMember().getNickname())
                .photo(review.getMember().getPhoto())
                .content(review.getContent())
                .build();
        return reply;
    }
}
