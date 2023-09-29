package com.prograngers.backend.dto.problem.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ShowProblemListResponse {
    private List<ProblemListResponse> problems;
    private int totalCount;
    private int pageNumber;

    public static ShowProblemListResponse from(List<ProblemListResponse> problemsList, int totalCount, int page) {
        return ShowProblemListResponse.builder()
                .problems(problemsList)
                .totalCount(totalCount)
                .pageNumber(page)
                .build();
    }
}
