package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LikeSolutionResponse {

    private ProblemResponse problem;
    private SolutionForLikeResponse solution;

    public static LikeSolutionResponse from(Solution solution) {
        Problem problem = solution.getProblem();
        return LikeSolutionResponse.builder()
                .problem(ProblemResponse.from(problem))
                .solution(SolutionForLikeResponse.from(solution))
                .build();
    }

}
