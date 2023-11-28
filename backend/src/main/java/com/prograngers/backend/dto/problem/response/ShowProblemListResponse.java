package com.prograngers.backend.dto.problem.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
