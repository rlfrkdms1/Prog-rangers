package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.review.Review;
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
    private int codeLineNumber;
    private String nickname;
    private String content;
    private List<ReviewWithRepliesResponse> replies;
    boolean mine;

    public static ReviewWithRepliesResponse from(Review review, ArrayList<ReviewWithRepliesResponse> replies,
                                                 boolean mine) {
        return ReviewWithRepliesResponse.builder()
                .id(review.getId())
                .codeLineNumber(review.getCodeLineNumber())
                .nickname(review.getMember().getNickname())
                .content(review.getContent())
                .replies(replies)
                .mine(mine)
                .build();
    }

    public static ReviewWithRepliesResponse from(Review review, boolean mine) {
        return ReviewWithRepliesResponse.builder()
                .id(review.getId())
                .codeLineNumber(review.getCodeLineNumber())
                .nickname(review.getMember().getNickname())
                .content(review.getContent())
                .mine(mine)
                .build();
    }
}
