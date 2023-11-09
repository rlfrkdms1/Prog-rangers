package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.dto.solution.response.SolutionForProfileResponse;
import com.prograngers.backend.entity.solution.Solution;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SolutionWithProblemResponse {

    private String problemTitle;
    private SolutionForProfileResponse solution;

    public static SolutionWithProblemResponse from(Solution solution) {
        return SolutionWithProblemResponse.builder()
                .problemTitle(solution.getProblem().getTitle())
                .solution(SolutionForProfileResponse.from(solution))
                .build();
    }
}
