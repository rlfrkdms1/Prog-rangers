package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.JudgeConstant;
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
    List<SolutionListSolution> solutionListSolutions;
    int totalPages;

    public static SolutionListResponse from(PageImpl<Solution> pages) {
        List<Solution> solutions = pages.getContent();
        if (solutions.size()==0){
            return null;
        }
        Problem problem = solutions.get(0).getProblem();

        // 문제이름, 저지명 세팅
        SolutionListResponse solutionListResponse = SolutionListResponse.builder()
                .problemName(problem.getTitle())
                .ojName(problem.getOjName())
                .solutionListSolutions(new ArrayList<>())
                .totalPages(pages.getTotalPages())
                .build();

        for (com.prograngers.backend.entity.Solution solution : solutions){
            solutionListResponse.getSolutionListSolutions().add(
                    SolutionListSolution.builder()
                            .solutionName(solution.getTitle())
                            .algorithm(solution.getAlgorithm())
                            .dataStructure(solution.getDataStructure())
                            .build()
            );
        }
        return solutionListResponse;
    }
}
