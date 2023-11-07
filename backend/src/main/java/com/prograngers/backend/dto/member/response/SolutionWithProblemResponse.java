package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.problem.JudgeConstant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SolutionWithProblemResponse {

    private String problemName;
    private String dataStructure;
    private String algorithm;
    private String language;
    private JudgeConstant ojName;
    private String description;
    private String[] code;

    public static SolutionWithProblemResponse from(Solution solution){
        return SolutionWithProblemResponse.builder()
                .problemName(solution.getProblem().getTitle())
                .dataStructure(solution.getDataStructure().getView())
                .algorithm(solution.getAlgorithm().getView())
                .language(solution.getLanguage().getView())
                .ojName(solution.getProblem().getOjName())
                .description(solution.getDescription())
                .code(solution.getCode().split("\n"))
                .build();
    }
}
