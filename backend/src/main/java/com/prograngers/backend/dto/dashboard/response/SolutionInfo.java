package com.prograngers.backend.dto.dashboard.response;

import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SolutionInfo {

    private String title;
    private String ojName;
    private Long solutionId;

    @Builder
    public SolutionInfo(String title, String ojName, Long solutionId) {
        this.title = title;
        this.ojName = ojName;
        this.solutionId = solutionId;
    }

    public static SolutionInfo of(Solution solution, Problem problem) {
        return SolutionInfo.builder()
                .title(solution.getTitle())
                .ojName(problem.getOjName().name())
                .solutionId(solution.getId())
                .build();
    }
}
