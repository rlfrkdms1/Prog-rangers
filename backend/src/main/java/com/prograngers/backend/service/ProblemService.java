package com.prograngers.backend.service;

import com.prograngers.backend.dto.problem.response.ProblemResponse;
import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.repository.problem.ProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<ProblemResponse> problemResponses = ProblemResponse.toDto(problems);
        return problemResponses;
    }
}
