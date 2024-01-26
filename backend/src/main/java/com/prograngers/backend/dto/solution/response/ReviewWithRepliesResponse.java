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
    private boolean updated;
    boolean mine;

    public static ReviewWithRepliesResponse from(Review review, ArrayList<ReviewWithRepliesResponse> replies,
                                                 boolean mine) {
        return ReviewWithRepliesResponse.builder()
                .id(review.getId())
                .codeLineNumber(review.getCodeLineNumber())
                .nickname(review.getMember().getNickname())
                .content(review.getContent())
                .replies(replies)
                .updated(review.isUpdated())
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
                .updated(review.isUpdated())
                .build();
    }
}
