package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.problem.JudgeConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolutionListResponse {
    String problemName;
    JudgeConstant ojName;
    List<SolutionAtSolutionListResponse> solutions;
    int totalPages;

    int page;

    public static SolutionListResponse from(PageImpl<Solution> pages, int page) {
        List<Solution> solutions = pages.getContent();
        if (solutions.size()==0){
            return null;
        }
        Problem problem = getProblem(solutions);
        SolutionListResponse solutionListResponse = addProblemNameAndOjNameAtResponse(pages, problem);
        addSolutionAtResponse(solutions, solutionListResponse);
        solutionListResponse.setPage(page);
        return solutionListResponse;
    }

    private static void addSolutionAtResponse(List<Solution> solutions, SolutionListResponse solutionListResponse) {
        solutions.stream()
                        .forEach((solution -> solutionListResponse.getSolutions().add(SolutionAtSolutionListResponse.builder()
                                .solutionName(solution.getTitle())
                                .algorithm(solution.getAlgorithm())
                                .dataStructure(solution.getDataStructure())
                                .build())));
    }

    private static SolutionListResponse addProblemNameAndOjNameAtResponse(PageImpl<Solution> pages, Problem problem) {
        // 문제이름, 저지명 세팅
        SolutionListResponse solutionListResponse = SolutionListResponse.builder()
                .problemName(problem.getTitle())
                .ojName(problem.getOjName())
                .solutions(new ArrayList<>())
                .totalPages(pages.getTotalPages())
                .build();
        return solutionListResponse;
    }

    private static Problem getProblem(List<Solution> solutions) {
        Problem problem = solutions.get(0).getProblem();
        return problem;
    }
}
