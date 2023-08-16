package com.prograngers.backend.dto.problem.response;

import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.JudgeConstant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Builder
public class ProblemResponse {
    String title;
    JudgeConstant ojName;

    List<Object> tags = new ArrayList<>();


    public static List<ProblemResponse> toDto(List<Problem> results) {
        // 반환할 dto 리스트
        List<ProblemResponse> problemResponses = new ArrayList<>();

        // 결과를  for문 돌면서 반환 dto를 만든다
        for (Problem result : results) {
            ProblemResponse problemResponse = ProblemResponse.builder()
                    .title(result.getTitle())
                    .ojName(result.getOjName())
                    .tags(new ArrayList<>())
                    .build();

            List<Solution> solutions = result.getSolutions();
            HashMap<Object,Integer> solutionAlgorithmCountMap = new HashMap<>();

            for (Solution solution : solutions) {
                solutionAlgorithmCountMap.put(solution.getAlgorithm(),solutionAlgorithmCountMap.getOrDefault(solution.getAlgorithm(),1)+1);
                solutionAlgorithmCountMap.put(solution.getDataStructure(),solutionAlgorithmCountMap.getOrDefault(solution.getDataStructure(),1)+1);
            }

            List<Object> keySet = new ArrayList<>(solutionAlgorithmCountMap.keySet());
            keySet.sort((num1,num2)->solutionAlgorithmCountMap.get(num2).compareTo(solutionAlgorithmCountMap.get(num1)));

            for (int i=0; i<keySet.size(); i++){
                if (i==3) break;
                Object tag = keySet.get(i);
                if (tag!=null){
                    problemResponse.getTags().add(keySet.get(i));
                }
            }


            problemResponses.add(problemResponse);
        }

        return problemResponses;
    }
}
