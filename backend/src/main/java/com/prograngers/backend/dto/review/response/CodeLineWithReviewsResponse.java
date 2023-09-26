package com.prograngers.backend.dto.review.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Builder
public class CodeLineWithReviewsResponse {
    private Integer codeLineNumber;
    private String code;
    private List<ReviewWithRepliesResponse> reviewWithRepliesRespons;

    public static CodeLineWithReviewsResponse from(String line, int lineNumber){
        return CodeLineWithReviewsResponse.builder()
                .codeLineNumber(lineNumber)
                .code(line)
                .build();
    }
}
