package com.prograngers.backend.service;

import com.prograngers.backend.dto.problem.response.ProblemListProblem;
import com.prograngers.backend.dto.problem.response.ProblemListResponse;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.sortconstant.SortConstant;
import com.prograngers.backend.repository.problem.ProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public ProblemListResponse getProblemList(
            Pageable pageable,
            AlgorithmConstant algorithm,
            DataStructureConstant dataStructure,
            SortConstant sortBy) {
        PageImpl<Problem> pageImpl = problemRepository.findAll(pageable, dataStructure, algorithm, sortBy);
        List<Problem> problems = pageImpl.getContent();
        List<ProblemListProblem> problemListProblemResponse = makeProblemList(problems);
        ProblemListResponse response = ProblemListResponse.from(problemListProblemResponse, pageImpl.getTotalPages(),pageable.getPageNumber());
        return response;
    }

    private static List<ProblemListProblem> makeProblemList(List<Problem> problems) {
        // 반환할 dto 리스트
        List<ProblemListProblem> problemListProblemResponse = new ArrayList<>();

        // 결과를  for문 돌면서 반환 dto를 만든다
        for (Problem problem : problems) {
            ProblemListProblem problemListProblem = ProblemListProblem.from(problem);
            List<Solution> solutions = problem.getSolutions();
            HashMap<Object, Integer> tagCountMap = new HashMap<>();

            solutions.stream()
                    .forEach((solution)->{
                        tagCountMap.put(solution.getAlgorithm(), tagCountMap.getOrDefault(solution.getAlgorithm(), 1) + 1);
                        tagCountMap.put(solution.getDataStructure(), tagCountMap.getOrDefault(solution.getDataStructure(), 1) + 1);
                    });

            List<Object> keySet = new ArrayList<>(tagCountMap.keySet());
            keySet.sort((num1, num2) -> tagCountMap.get(num2).compareTo(tagCountMap.get(num1)));

            countTag(problemListProblem, keySet);
            problemListProblemResponse.add(problemListProblem);
        }
        return problemListProblemResponse;
    }

    private static void countTag(ProblemListProblem problemListProblem, List<Object> keySet) {
        for (int tagCount = 0; tagCount < keySet.size(); tagCount++) {
            if (tagCount == 3) break;
            Object tag = keySet.get(tagCount);
            if (tag != null) {
                problemListProblem.getTags().add(keySet.get(tagCount));
            }
        }
    }
}
