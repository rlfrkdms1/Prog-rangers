package com.prograngers.backend.dto.problem.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProblemListResponse {
    private List<ProblemListProblem> problems;
    private int totalCount;

    public static ProblemListResponse from(List<ProblemListProblem> listProblems, int totalCount){
        return new ProblemListResponse(listProblems,totalCount);
    }

}
