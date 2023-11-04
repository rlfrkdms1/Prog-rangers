package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SolutionForListResponse {

    private Long solutionId;
    private String title;
    private String algorithm;
    private String dataStructure;
    private String language;
    private int level;
    private boolean isScrapped;

    public static SolutionForListResponse from(Solution solution) {
        SolutionForListResponse response = SolutionForListResponse.builder()
                .solutionId(solution.getId())
                .title(solution.getTitle())
                .language(solution.getLanguage().getView())
                .level(solution.getLevel())
                .algorithm(solution.getAlgorithmView())
                .dataStructure(solution.getDataStructureView())
                .isScrapped(solution.isScrapped())
                .build();
        return response;
    }

}
