package com.prograngers.backend.service;

import com.prograngers.backend.dto.problem.response.ProblemResponse;
import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.repository.problem.ProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProblemService {

    private final ProblemRepository problemRepository;

    public List<ProblemResponse> getProblemList(
            Integer page,
            AlgorithmConstant algorithm,
            DataStructureConstant dataStructure,
            String sortBy) {
        List<Problem> problems = problemRepository.findAll(page, dataStructure, algorithm, sortBy);
        // 반환할 dto 리스트
        List<ProblemResponse> problemResponses = new ArrayList<>();
        // 결과를  for문 돌면서 반환 dto를 만든다
        for (Problem problem : problems) {
            ProblemResponse problemResponse = ProblemResponse.from(problem);
            List<Solution> solutions = problem.getSolutions();
            HashMap<Object, Integer> solutionAlgorithmCountMap = new HashMap<>();
            for (Solution solution : solutions) {
                solutionAlgorithmCountMap.put(solution.getAlgorithm(), solutionAlgorithmCountMap.getOrDefault(solution.getAlgorithm(), 1) + 1);
                solutionAlgorithmCountMap.put(solution.getDataStructure(), solutionAlgorithmCountMap.getOrDefault(solution.getDataStructure(), 1) + 1);
            }
            List<Object> keySet = new ArrayList<>(solutionAlgorithmCountMap.keySet());
            keySet.sort((num1, num2) -> solutionAlgorithmCountMap.get(num2).compareTo(solutionAlgorithmCountMap.get(num1)));

            for (int i = 0; i < keySet.size(); i++) {
                if (i == 3) break;
                Object tag = keySet.get(i);
                if (tag != null) {
                    problemResponse.getTags().add(keySet.get(i));
                }
            }
            problemResponses.add(problemResponse);
        }
        return problemResponses;
    }
}
