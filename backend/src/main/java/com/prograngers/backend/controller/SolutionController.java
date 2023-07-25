package com.prograngers.backend.controller;

import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.service.SolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/solutions")
public class SolutionController {

    private final SolutionService solutionService;

    @PostMapping("/new-form")
    public ResponseEntity<?> newForm(@RequestBody @Validated SolutionRequest solutionRequest, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(new ErrorResponse());
        }

        // 아직 API 설계 X

        Solution solution = solutionService.save();

        return ResponseEntity.ok().body(new SolutionResponse());
    }






}
