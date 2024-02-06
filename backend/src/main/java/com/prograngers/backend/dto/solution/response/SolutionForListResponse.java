package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SolutionForListResponse {

    private Long id;
    private String title;
    private String algorithm;
    private String dataStructure;
    private String language;
    private int level;
    private boolean byScrap;
    private boolean isPublic;

    public static SolutionForListResponse from(Solution solution) {
        return SolutionForListResponse.builder()
                .id(solution.getId())
                .title(solution.getTitle())
                .language(solution.getLanguage().getView())
                .level(solution.getLevel())
                .algorithm(solution.getAlgorithmName())
                .dataStructure(solution.getDataStructureName())
                .byScrap(solution.isScrapped())
                .isPublic(solution.isPublic())
                .build();
    }
}
