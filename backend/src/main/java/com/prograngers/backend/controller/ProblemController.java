package com.prograngers.backend.controller;

import com.prograngers.backend.dto.problem.response.ProblemListResponse;
import com.prograngers.backend.dto.solution.response.SolutionListResponse;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.entity.sortconstant.SortConstant;
import com.prograngers.backend.service.ProblemService;
import com.prograngers.backend.service.SolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.prograngers.backend.entity.sortconstant.SortConstant.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/problems")
@Slf4j
public class ProblemController {

    private final ProblemService problemService;

    private final SolutionService solutionService;

    private final String SORT_CONSTANT_DEFAULT = "NEWEST";

    // problem 목록보기
    @GetMapping
    public ResponseEntity<?> problems(
            @PageableDefault(size = 5)Pageable pageable,
            @RequestParam(required = false) AlgorithmConstant algorithm,
            @RequestParam(required = false) DataStructureConstant dataStructure,
            @RequestParam(defaultValue = SORT_CONSTANT_DEFAULT) SortConstant sortBy) {
        ProblemListResponse problemList  = problemService.getProblemList(pageable, algorithm, dataStructure, sortBy);
        return ResponseEntity.ok(problemList);
    }


    // solution 목록보기
    @GetMapping("{problemId}/solutions")
    public ResponseEntity<?> solutionList(
            @PageableDefault(size = 5)Pageable pageable,
            @PathVariable Long problemId,
            @RequestParam(required = false) LanguageConstant language,
            @RequestParam(required = false) AlgorithmConstant algorithm,
            @RequestParam(required = false) DataStructureConstant dataStructure,
            @RequestParam(defaultValue = SORT_CONSTANT_DEFAULT) SortConstant sortBy
    ){
        SolutionListResponse solutionListResponse = solutionService.getSolutionList(pageable, problemId, language,algorithm,dataStructure,sortBy);
        return ResponseEntity.ok().body(solutionListResponse);
    }

}
