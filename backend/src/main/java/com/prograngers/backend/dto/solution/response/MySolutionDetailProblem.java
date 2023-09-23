package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.problem.JudgeConstant;
import lombok.Builder;

@Builder
public class MySolutionDetailProblem {
    private String problemTitle;
    private JudgeConstant ojName;
    public static MySolutionDetailProblem from(String problemTitle, JudgeConstant ojName){
        return MySolutionDetailProblem.builder()
                .problemTitle(problemTitle)
                .ojName(ojName)
                .build();
    }
}
