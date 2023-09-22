package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class SolutionDetailResponse {

    private SolutionDetailProblem problem;
    private SolutionDetailSolution solution;
    private List<SolutionDetailComment> comments;

    public static SolutionDetailResponse from(SolutionDetailProblem problem, SolutionDetailSolution solution,
                                              List<SolutionDetailComment> comments) {
        return SolutionDetailResponse.builder()
                .problem(problem)
                .solution(solution)
                .comments(comments)
                .build();
    }
}
