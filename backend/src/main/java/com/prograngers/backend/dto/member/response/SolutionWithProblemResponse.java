package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.problem.JudgeConstant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SolutionWithProblemResponse {

    private String problemName;
    private Long id;
    private String dataStructure;
    private String algorithm;
    private String language;
    private JudgeConstant ojName;
    private String description;
    private String[] code;

    public static SolutionWithProblemResponse from(Solution solution) {
        return SolutionWithProblemResponse.builder()
                .problemName(solution.getProblem().getTitle())
                .id(solution.getId())
                .dataStructure(solution.getDataStructureView())
                .algorithm(solution.getAlgorithmView())
                .language(solution.getLanguage().getView())
                .ojName(solution.getProblem().getOjName())
                .description(solution.getDescription())
                .code(solution.getCode().split("\n"))
                .build();
    }
}
