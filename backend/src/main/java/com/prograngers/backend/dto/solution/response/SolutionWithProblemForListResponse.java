package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SolutionWithProblemForListResponse {

    private ProblemResponse problem;
    private SolutionForListResponse solution;

    public static SolutionWithProblemForListResponse from(Solution solution) {
        Problem problem = solution.getProblem();
        return SolutionWithProblemForListResponse.builder()
                .problem(ProblemResponse.from(problem))
                .solution(SolutionForListResponse.from(solution))
                .build();
    }

}
