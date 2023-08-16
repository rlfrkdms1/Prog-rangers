package com.prograngers.backend.dto.review;

import com.prograngers.backend.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class ReviewResponse {
    Long id;
    String nickname;
    String photo;
    String content;
    List<ReplyResponse> replies = new ArrayList<>();

    public static ReviewResponse from(Review review){

        ReviewResponse reviewResponse = ReviewResponse.builder()
                .id(review.getId())
                .nickname(review.getMember().getNickname())
                .photo(review.getMember().getPhoto())
                .content(review.getContent())
                .replies(new ArrayList<>())
                .build();
        return reviewResponse;
    }
}
