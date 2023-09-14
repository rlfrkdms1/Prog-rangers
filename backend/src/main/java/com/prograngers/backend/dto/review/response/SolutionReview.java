package com.prograngers.backend.dto.review.response;

import com.prograngers.backend.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class SolutionReview {
    Long id;
    String nickname;
    String photo;
    String content;
    List<SolutionReviewReply> replies;

    public static SolutionReview from(Review review){

        SolutionReview solutionReviewResponse = SolutionReview.builder()
                .id(review.getId())
                .nickname(review.getMember().getNickname())
                .photo(review.getMember().getPhoto())
                .content(review.getContent())
                .replies(new ArrayList<>())
                .build();
        return solutionReviewResponse;
    }
}
