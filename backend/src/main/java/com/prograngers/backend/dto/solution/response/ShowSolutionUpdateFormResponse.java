package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowSolutionUpdateFormResponse {

    private String title;
    private String link;
    private String algorithm;
    private String dataStructure;
    private String[] code;
    private String description;
    private int level;

    public static ShowSolutionUpdateFormResponse from(Solution solution) {
        return ShowSolutionUpdateFormResponse.builder()
                .title(solution.getTitle())
                .link(solution.getProblem()
                        .getLink())
                .algorithm(solution.getAlgorithmName())
                .dataStructure(solution.getDataStructureName())
                .code(solution.getCode().split("\n"))
                .level(solution.getLevel())
                .description(solution.getDescription()).build();
    }
}
