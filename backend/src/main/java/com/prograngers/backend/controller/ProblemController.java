package com.prograngers.backend.controller;

import com.prograngers.backend.dto.problem.response.ShowProblemListResponse;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.sortconstant.SortConstant;
import com.prograngers.backend.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/problems")
@Slf4j
public class ProblemController {

    private final ProblemService problemService;
    private final String SORT_CONSTANT_DEFAULT = "NEWEST";

    @GetMapping
    public ShowProblemListResponse problems(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) AlgorithmConstant algorithm,
            @RequestParam(required = false) DataStructureConstant dataStructure,
            @RequestParam(defaultValue = SORT_CONSTANT_DEFAULT) SortConstant sortBy) {
        return problemService.getProblemList(pageable, algorithm, dataStructure, sortBy);
    }
}
