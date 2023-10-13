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
public class ShowSolutionListResponse {

    private Long problemId;
    private String problemName;
    private JudgeConstant ojName;
    private List<SolutionAtSolutionListResponse> solutions;
    private int totalPages;
    private int page;

    public static ShowSolutionListResponse of(PageImpl<Solution> pages, int page) {
        List<Solution> solutions = pages.getContent();
        if (solutions.size()==0){
            return null;
        }
        Problem problem = getProblem(solutions);
        ShowSolutionListResponse showSolutionListResponse = makeResponse(pages, problem);
        addSolutionAtResponse(solutions, showSolutionListResponse);
        showSolutionListResponse.setPage(page);
        return showSolutionListResponse;
    }

    private static void addSolutionAtResponse(List<Solution> solutions, ShowSolutionListResponse showSolutionListResponse) {
        solutions
                .stream()
                .forEach((solution -> showSolutionListResponse.getSolutions().add(SolutionAtSolutionListResponse.from(solution))));
    }

    private static ShowSolutionListResponse makeResponse(PageImpl<Solution> pages, Problem problem) {
        ShowSolutionListResponse showSolutionListResponse = ShowSolutionListResponse.builder()
                .problemId(problem.getId())
                .problemName(problem.getTitle())
                .ojName(problem.getOjName())
                .solutions(new ArrayList<>())
                .totalPages(pages.getTotalPages())
                .build();
        return showSolutionListResponse;
    }

    private static Problem getProblem(List<Solution> solutions) {
        Problem problem = solutions.get(0).getProblem();
        return problem;
    }
}
