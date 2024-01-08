package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.solution.Solution;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SolutionForProfileResponse {
    private static final String REGEX = System.lineSeparator();
    private String title;
    private Long id;
    private String dataStructure;
    private String algorithm;
    private String language;
    private JudgeConstant ojName;
    private String description;
    private String[] code;

    public static SolutionForProfileResponse from(Solution solution) {
        return SolutionForProfileResponse.builder()
                .title(solution.getTitle())
                .id(solution.getId())
                .dataStructure(solution.getDataStructureName())
                .algorithm(solution.getAlgorithmName())
                .language(solution.getLanguage().getView())
                .ojName(solution.getProblem().getOjName())
                .description(solution.getDescription())
                .code(solution.getCode().split(REGEX))
                .build();
    }
}
