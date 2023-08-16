package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.JudgeConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    List<SolutionResponse> solutions;

    public static SolutionListResponse createDto(List<Solution> solutions) {
        if (solutions.size()==0){
            return null;
        }
        // 문제이름, 저지명 세팅
        SolutionListResponse solutionListResponse = SolutionListResponse.builder()
                .problemName(solutions.get(0).getProblem().getTitle())
                .ojName(solutions.get(0).getProblem().getOjName())
                .solutions(new ArrayList<>())
                .build();

        for (Solution solution : solutions){
            solutionListResponse.getSolutions().add(
                    SolutionResponse.builder()
                            .solutionName(solution.getTitle())
                            .algorithm(solution.getAlgorithm())
                            .dataStructure(solution.getDataStructure())
                            .build()
            );
        }
        return solutionListResponse;
    }
}
