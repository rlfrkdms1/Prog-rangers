package com.prograngers.backend.dto.review.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class Review {
    Long id;
    String nickname;
    String photo;
    String content;
    List<Reply> replies = new ArrayList<>();

    public static Review from(com.prograngers.backend.entity.Review review){

        Review reviewResponse = Review.builder()
                .id(review.getId())
                .nickname(review.getMember().getNickname())
                .photo(review.getMember().getPhoto())
                .content(review.getContent())
                .replies(new ArrayList<>())
                .build();
        return reviewResponse;
    }
}
