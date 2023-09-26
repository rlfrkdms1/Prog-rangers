package com.prograngers.backend.dto.problem.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ShowProblemListResponse {
    private List<ProblemListResponse> problems;
    private int totalCount;

    private int pageNumber;

    public static ShowProblemListResponse from(List<ProblemListResponse> listProblems, int totalCount, int page){
        return new ShowProblemListResponse(listProblems,totalCount,page);
    }

}
