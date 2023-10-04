package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SolutionWithProblemForListResponse {

    private Long solutionId;
    private JudgeConstant judge;
    private String title;
    private String algorithm;
    private String dataStructure;
    private String language;
    private int level;

    public static SolutionWithProblemForListResponse from(Solution solution) {
        Problem problem = solution.getProblem();
        SolutionWithProblemForListResponse response = SolutionWithProblemForListResponse.builder()
                .solutionId(solution.getId())
                .judge(problem.getOjName())
                .title(solution.getTitle())
                .language(solution.getLanguage().getView())
                .level(solution.getLevel())
                .build();
        if(solution.getAlgorithm() != null) response.setAlgorithm(solution.getAlgorithm().getView());
        if(solution.getDataStructure() != null) response.setDataStructure(solution.getDataStructure().getView());
        return response;
    }

    private void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    private void setDataStructure(String dataStructure) {
        this.dataStructure = dataStructure;
    }
}
