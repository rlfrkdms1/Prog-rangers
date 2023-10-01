package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.problem.JudgeConstant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProblemResponse {
    private String problemTitle;
    private JudgeConstant ojName;
    public static ProblemResponse from(String problemTitle, JudgeConstant ojName){
        return ProblemResponse.builder()
                .problemTitle(problemTitle)
                .ojName(ojName)
                .build();
    }
}
