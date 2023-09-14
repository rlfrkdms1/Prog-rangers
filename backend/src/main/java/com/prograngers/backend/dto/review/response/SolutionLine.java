package com.prograngers.backend.dto.review.response;

import com.prograngers.backend.entity.solution.Solution;
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
public class SolutionLine {
    private Integer codeLineNumber;
    private String code;
    private List<SolutionReview> solutionReviews;

    public static SolutionLine from(String line, int lineNumber){
        return SolutionLine.builder()
                .codeLineNumber(lineNumber)
                .code(line)
                .build();
    }
}
