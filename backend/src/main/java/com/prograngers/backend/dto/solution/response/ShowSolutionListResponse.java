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
        ShowSolutionListResponse showSolutionListResponse = addProblemNameAndOjNameAtResponse(pages, problem);
        addSolutionAtResponse(solutions, showSolutionListResponse);
        showSolutionListResponse.setPage(page);
        return showSolutionListResponse;
    }

    private static void addSolutionAtResponse(List<Solution> solutions, ShowSolutionListResponse showSolutionListResponse) {
        solutions.stream()
                        .forEach((solution -> showSolutionListResponse.getSolutions().add(SolutionAtSolutionListResponse.builder()
                                .solutionName(solution.getTitle())
                                .algorithm(solution.getAlgorithmView())
                                .dataStructure(solution.getDataStructureView())
                                .build())));
    }

    private static ShowSolutionListResponse addProblemNameAndOjNameAtResponse(PageImpl<Solution> pages, Problem problem) {
        // 문제이름, 저지명 세팅
        ShowSolutionListResponse showSolutionListResponse = ShowSolutionListResponse.builder()
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
