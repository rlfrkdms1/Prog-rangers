package com.prograngers.backend.controller;

import com.prograngers.backend.dto.problem.response.ProblemResponse;
import com.prograngers.backend.dto.solution.response.SolutionListResponse;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.LanguageConstant;
import com.prograngers.backend.service.ProblemService;
import com.prograngers.backend.service.SolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problems")
@Slf4j
public class ProblemController {

    private final ProblemService problemService;

    private final SolutionService solutionService;

    // problem 목록보기
    @GetMapping
    public ResponseEntity<?> problems(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(required = false) AlgorithmConstant algorithm,
            @RequestParam(required = false) DataStructureConstant dataStructure,
            @RequestParam(defaultValue = "date") String sortBy) {
        List<ProblemResponse> problemList = problemService.getProblemList(page, algorithm, dataStructure, sortBy);
        return ResponseEntity.ok(problemList);
    }


    // solution 목록보기
    @GetMapping("{problemId}/solutions")
    public ResponseEntity<?> solutionList(
            Pageable pageable,
            @PathVariable Long problemId,
            @RequestParam(required = false) LanguageConstant language,
            @RequestParam(required = false) AlgorithmConstant algorithm,
            @RequestParam(required = false) DataStructureConstant dataStructure,
            @RequestParam(defaultValue = "newest") String sortBy
    ){
        SolutionListResponse solutionListResponse = solutionService.getSolutionList(pageable, problemId, language,algorithm,dataStructure,sortBy);
        return ResponseEntity.ok().body(solutionListResponse);
    }

}
