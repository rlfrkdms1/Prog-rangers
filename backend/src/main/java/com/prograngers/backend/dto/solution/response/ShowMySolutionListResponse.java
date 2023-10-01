package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class ShowMySolutionListResponse {

    private int page;
    private int totalPage;
    private List<SolutionWithProblemForListResponse> solutions;

    public static ShowMySolutionListResponse from(Page<Solution> solutions) {
        return ShowMySolutionListResponse.builder()
                .page(solutions.getNumber())
                .totalPage(solutions.getTotalPages())
                .solutions(solutions.getContent().stream().map(SolutionWithProblemForListResponse::from).collect(Collectors.toList()))
                .build();
    }

}
