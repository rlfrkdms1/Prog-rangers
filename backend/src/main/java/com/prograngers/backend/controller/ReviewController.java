package com.prograngers.backend.controller;

import com.prograngers.backend.dto.review.SolutionReviewsResponse;
import com.prograngers.backend.service.SolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{solutionId}")
@RequiredArgsConstructor
public class ReviewController {

    private final SolutionService solutionService;

    // 상세보기 한 줄 리뷰
    @GetMapping("/reviews")
    public ResponseEntity<?> solutionReviews(@PathVariable Long solutionId){
        SolutionReviewsResponse reviewDetail = solutionService.getReviewDetail(solutionId);
        return ResponseEntity.ok().body(reviewDetail);
    }
}
