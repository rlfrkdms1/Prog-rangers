package com.prograngers.backend.service;

import com.prograngers.backend.dto.problem.response.ProblemListResponse;
import com.prograngers.backend.dto.problem.response.ShowProblemListResponse;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.sortconstant.SortConstant;
import com.prograngers.backend.repository.problem.ProblemRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProblemService {

    private static final int MAX_TAG_COUNT = 3;

    private final ProblemRepository problemRepository;

    public ShowProblemListResponse getProblemList(
            Pageable pageable,
            AlgorithmConstant algorithm,
            DataStructureConstant dataStructure,
            SortConstant sortBy) {
        PageImpl<Problem> pageImpl = problemRepository.findAll(pageable, dataStructure, algorithm, sortBy);
        List<Problem> problems = pageImpl.getContent();
        List<ProblemListResponse> problemListResponseResponse = makeProblemList(problems);
        ShowProblemListResponse response = ShowProblemListResponse.from(problemListResponseResponse, pageImpl);
        return response;
    }

    private List<ProblemListResponse> makeProblemList(List<Problem> problems) {
        // 반환할 dto 리스트
        List<ProblemListResponse> problemListResponseResponse = new ArrayList<>();

        // 결과를  for문 돌면서 반환 dto를 만든다
        problems.stream()
                .forEach(problem -> {
                    ProblemListResponse problemListResponse = ProblemListResponse.from(problem);
                    List<Solution> solutions = problem.getSolutions();

                    addTagToProblemListProblem(problemListResponse, solutions);
                    problemListResponseResponse.add(problemListResponse);
                });

        return problemListResponseResponse;
    }

    private void addTagToProblemListProblem(ProblemListResponse problemListResponse, List<Solution> solutions) {
        HashMap<Object, Integer> tagCountMap = new HashMap<>();

        solutions.stream()
                .forEach((solution) -> {
                    tagCountMap.put(solution.getAlgorithm(), tagCountMap.getOrDefault(solution.getAlgorithm(), 1) + 1);
                    tagCountMap.put(solution.getDataStructure(),
                            tagCountMap.getOrDefault(solution.getDataStructure(), 1) + 1);
                });

        ArrayList<Object> tagList = new ArrayList<>();
        tagCountMap.keySet()
                .stream()
                .forEach((tag) -> tagList.add(tag));

        // 태그 개수에 따라 정렬
        tagList.sort((tag1, tag2) -> tagCountMap.get(tag2).compareTo(tagCountMap.get(tag1)));

        addTag(problemListResponse, tagList);
    }

    private void addTag(ProblemListResponse problemListResponse, List<Object> tagList) {
        int tagCount = 0;
        while (tagCount < MAX_TAG_COUNT) {
            if (tagList.size() > tagCount) {
                problemListResponse.getTags().add(tagList.get(tagCount));
            }
            tagCount += 1;
        }
    }
}
