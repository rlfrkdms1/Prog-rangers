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

    private int pageNumber;

    public static ProblemListResponse from(List<ProblemListProblem> listProblems, int totalCount, int page){
        return new ProblemListResponse(listProblems,totalCount,page);
    }

}
