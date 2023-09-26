package com.prograngers.backend.dto.solution.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class ShowSolutionDetailWithProblemAndCommentsResponse {

    private ProblemResponse problem;
    private SolutionResponse solution;
    private List<CommentWithRepliesResponse> comments;

    public static ShowSolutionDetailWithProblemAndCommentsResponse from(ProblemResponse problem, SolutionResponse solution,
                                                                        List<CommentWithRepliesResponse> comments) {
        return ShowSolutionDetailWithProblemAndCommentsResponse.builder()
                .problem(problem)
                .solution(solution)
                .comments(comments)
                .build();
    }
}
