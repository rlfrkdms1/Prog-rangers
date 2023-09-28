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
public class CodeWithCodeLineNumberAndReviewResponse {
    private Integer codeLineNumber;
    private String code;
    private List<ReviewWithRepliesResponse> reviewWithRepliesResponse;

    public static CodeWithCodeLineNumberAndReviewResponse from(String line, int lineNumber){
        return CodeWithCodeLineNumberAndReviewResponse.builder()
                .codeLineNumber(lineNumber)
                .code(line)
                .build();
    }
}