package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.solution.Solution;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SolutionAtSolutionListResponse {
    private final Long id;
    private final String title;
    private final String algorithm;
    private final String dataStructure;

    public static SolutionAtSolutionListResponse from(Solution solution) {
        return SolutionAtSolutionListResponse
                .builder()
                .id(solution.getId())
                .title(solution.getTitle())
                .algorithm(solution.getAlgorithmName())
                .dataStructure(solution.getDataStructureName())
                .build();
    }
}
