package com.prograngers.backend.dto.response.dashboard;

import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyRecentSolutionInfo {

    private String title;
    private String ojName;
    private Long solutionId;

    public static MyRecentSolutionInfo of(Solution solution, Problem problem) {
        return MyRecentSolutionInfo.builder()
                .title(solution.getTitle())
                .ojName(problem.getOjName().name())
                .solutionId(solution.getId())
                .build();
    }
}
