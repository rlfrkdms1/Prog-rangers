package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.HashTag;
import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class SolutionWithProblemForListResponse {

    private Long solutionId;
    private JudgeConstant judge;
    private String title;
    private List<HashTag> tags;
    private int level;

    public static SolutionWithProblemForListResponse from(Solution solution) {
        Problem problem = solution.getProblem();
        return SolutionWithProblemForListResponse.builder()
                .solutionId(solution.getId())
                .judge(problem.getOjName())
                .title(solution.getTitle())
                .tags(List.of(solution.getAlgorithm(), solution.getDataStructure(), solution.getLanguage()))
                .build();
    }
}
