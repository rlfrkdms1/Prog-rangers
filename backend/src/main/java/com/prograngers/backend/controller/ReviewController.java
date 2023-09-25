package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.dto.review.response.SolutionReviewsResponse;
import com.prograngers.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("prog-rangers/solutions/{solutionId}")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 상세보기 한 줄 리뷰
    @GetMapping("/reviews")
    public ResponseEntity<?> solutionReviews(@PathVariable Long solutionId, @LoggedInMember(required = false) Long memberId){
        SolutionReviewsResponse reviewDetail = reviewService.getReviewDetail(solutionId, memberId);
        return ResponseEntity.ok().body(reviewDetail);
    }
}
