package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageImpl;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowSolutionListResponse {

    private String problemName;
    private JudgeConstant ojName;
    private List<SolutionAtSolutionListResponse> solutions;
    private int totalPages;
    private int page;

    public static ShowSolutionListResponse from(PageImpl<Solution> pages) {
        List<Solution> solutions = pages.getContent();
        if (solutions.size() == 0) {
            return null;
        }
        Problem problem = getProblem(solutions);
        ShowSolutionListResponse showSolutionListResponse = addProblemNameAndOjNameAtResponse(pages, problem);
        addSolutionAtResponse(solutions, showSolutionListResponse);
        return showSolutionListResponse;
    }

    private static void addSolutionAtResponse(List<Solution> solutions,
                                              ShowSolutionListResponse showSolutionListResponse) {
        solutions.stream()
                .forEach((solution -> showSolutionListResponse.getSolutions()
                        .add(SolutionAtSolutionListResponse.from(solution))));
    }

    private static ShowSolutionListResponse addProblemNameAndOjNameAtResponse(PageImpl<Solution> pages,
                                                                              Problem problem) {
        ShowSolutionListResponse showSolutionListResponse = ShowSolutionListResponse.builder()
                .problemName(problem.getTitle())
                .ojName(problem.getOjName())
                .solutions(new ArrayList<>())
                .totalPages(pages.getTotalPages())
                .page(pages.getNumber())
                .build();
        return showSolutionListResponse;
    }

    private static Problem getProblem(List<Solution> solutions) {
        Problem problem = solutions.get(0).getProblem();
        return problem;
    }
}
