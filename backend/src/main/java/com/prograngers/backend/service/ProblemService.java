package com.prograngers.backend.service;

import com.prograngers.backend.dto.problem.ProblemListResponse;
import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {

    private final ProblemRepository problemRepository;

    public List<ProblemListResponse> getProblemList(String algorithm, String dataStructure, String sortBy) {
        List<ProblemListResponse> problemListResponses = new ArrayList<>();
        List<Problem> list = problemRepository.findAll();
        for (Problem problem : list) {
            problemListResponses.add(new ProblemListResponse(problem.getTitle(), problem.getOjName()));
        }
        return problemListResponses;
    }
}
