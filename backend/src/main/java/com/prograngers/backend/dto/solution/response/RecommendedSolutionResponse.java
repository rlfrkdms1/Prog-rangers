package com.prograngers.backend.dto.solution.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendedSolutionResponse {
    private Long id;
    private String solutionName;

    public static RecommendedSolutionResponse from(Long id, String solutionName){
        return RecommendedSolutionResponse.builder()
                .id(id)
                .solutionName(solutionName)
                .build();
    }
}
