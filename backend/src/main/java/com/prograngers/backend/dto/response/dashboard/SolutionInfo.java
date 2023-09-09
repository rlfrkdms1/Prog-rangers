package com.prograngers.backend.dto.response.dashboard;

import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SolutionInfo {

    private String title;
    private String ojName;
    private Long solutionId;

    public static SolutionInfo of(Solution solution, Problem problem) {
        return SolutionInfo.builder()
                .title(solution.getTitle())
                .ojName(problem.getOjName().name())
                .solutionId(solution.getId())
                .build();
    }
}
