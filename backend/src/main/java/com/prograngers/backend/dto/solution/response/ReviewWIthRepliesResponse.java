package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class ReviewWIthRepliesResponse {
    private Long id;
    private int codeLineNumber;
    private String nickname;
    private String content;
    private List<ReviewWIthRepliesResponse> replies;

    boolean mine;

    public static ReviewWIthRepliesResponse from(Review review, ArrayList<ReviewWIthRepliesResponse> replies, boolean mine) {
        return ReviewWIthRepliesResponse.builder()
                .id(review.getId())
                .codeLineNumber(review.getCodeLineNumber())
                .nickname(review.getMember().getNickname())
                .content(review.getContent())
                .replies(replies)
                .mine(mine)
                .build();
    }

    public static ReviewWIthRepliesResponse from(Review review,  boolean mine) {
        return ReviewWIthRepliesResponse.builder()
                .id(review.getId())
                .codeLineNumber(review.getCodeLineNumber())
                .nickname(review.getMember().getNickname())
                .content(review.getContent())
                .mine(mine)
                .build();
    }
}
