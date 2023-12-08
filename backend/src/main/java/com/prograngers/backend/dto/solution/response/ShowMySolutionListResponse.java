package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.solution.Solution;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
@Builder
public class ShowMySolutionListResponse {

    private int page;
    private int totalPage;
    private List<SolutionWithProblemForListResponse> contents;

    public static ShowMySolutionListResponse from(Page<Solution> solutions) {
        return ShowMySolutionListResponse.builder()
                .page(solutions.getNumber())
                .totalPage(solutions.getTotalPages())
                .contents(solutions.getContent().stream()
                        .map(SolutionWithProblemForListResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }

}
