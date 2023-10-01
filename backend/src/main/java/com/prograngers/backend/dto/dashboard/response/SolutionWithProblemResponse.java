package com.prograngers.backend.dto.dashboard.response;

import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SolutionWithProblemResponse {

    private String title;
    private String ojName;
    private Long solutionId;

    @Builder
    public SolutionWithProblemResponse(String title, String ojName, Long solutionId) {
        this.title = title;
        this.ojName = ojName;
        this.solutionId = solutionId;
    }

    public static SolutionWithProblemResponse of(Solution solution, Problem problem) {
        return SolutionWithProblemResponse.builder()
                .title(solution.getTitle())
                .ojName(problem.getOjName().name())
                .solutionId(solution.getId())
                .build();
    }
}
