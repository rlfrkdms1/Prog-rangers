package com.prograngers.backend.controller;


import com.prograngers.backend.dto.problem.ProblemAlgorithmDataStructureResponse;
import com.prograngers.backend.dto.problem.ProblemListResponse;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public ResponseEntity<?> problems(@RequestParam Integer page, @RequestParam(required = false) AlgorithmConstant algorithm, @RequestParam(required = false) DataStructureConstant dataStructure, @RequestParam(defaultValue = "date") String sortBy) {
        List<ProblemAlgorithmDataStructureResponse> problemListResponses = problemService.getProblemList(page, algorithm, dataStructure, sortBy);
        return ResponseEntity.ok(problemListResponses);
    }
}
