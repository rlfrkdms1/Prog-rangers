package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.problem.JudgeConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class SolutionDetailProblem {
    private String name;
    private JudgeConstant ojName;

    public static SolutionDetailProblem from(Problem problem){
        return new SolutionDetailProblem(problem.getTitle(),problem.getOjName());
    }

}
