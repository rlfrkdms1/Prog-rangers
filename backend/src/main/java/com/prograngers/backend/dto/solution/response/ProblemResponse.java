package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.problem.Problem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProblemResponse {

    private String title;
    private JudgeConstant ojName;

    public static ProblemResponse from(String problemTitle, JudgeConstant ojName) {
        return ProblemResponse.builder()
                .title(problemTitle)
                .ojName(ojName)
                .build();
    }

    public static ProblemResponse from(Problem problem) {
        return ProblemResponse.builder()
                .title(problem.getTitle())
                .ojName(problem.getOjName())
                .build();
    }

}
