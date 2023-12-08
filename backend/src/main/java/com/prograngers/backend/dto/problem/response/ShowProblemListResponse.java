package com.prograngers.backend.dto.problem.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageImpl;

@Getter
@Setter
@Builder
public class ShowProblemListResponse {
    private List<ProblemListResponse> problems;
    private int totalCount;
    private int pageNumber;

    public static ShowProblemListResponse from(List<ProblemListResponse> problemsList, PageImpl page) {
        return ShowProblemListResponse.builder()
                .problems(problemsList)
                .totalCount(page.getTotalPages())
                .pageNumber(page.getNumber())
                .build();
    }
}
