package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;

import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.review.request.UpdateReviewRequest;
import com.prograngers.backend.dto.review.request.WriteReviewRequest;
import com.prograngers.backend.dto.review.response.ShowReviewsResponse;
import com.prograngers.backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("api/v1/solutions/{solutionId}")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final String REDIRECT_PATH = "http://localhost:8080/prog-rangers/solutions";

    // 상세보기 한 줄 리뷰
    @GetMapping("/reviews")
    public ResponseEntity<?> solutionReviews(@PathVariable Long solutionId, @LoggedInMember(required = false) Long memberId){
        ShowReviewsResponse reviewDetail = reviewService.getReviewDetail(solutionId, memberId);
        return ResponseEntity.ok().body(reviewDetail);
    }

    @PostMapping("/reviews")
    @Login
    public ResponseEntity<?> writeReview(@PathVariable Long solutionId, @Valid  @RequestBody WriteReviewRequest writeReviewRequest, @LoggedInMember Long memberId){
        reviewService.writeReview(writeReviewRequest,memberId,solutionId);
        // 풀이 상세보기로 리다이렉트
        return redirect(solutionId);
    }

    @PatchMapping("/reviews/{reviewId}")
    @Login
    public ResponseEntity<?> updateReview(@PathVariable Long solutionId, @Valid  @RequestBody UpdateReviewRequest updateReviewRequest,
                                         @LoggedInMember Long memberId,@PathVariable Long reviewId){
        reviewService.updateReview(updateReviewRequest,memberId,reviewId);
        // 풀이 상세보기로 리다이렉트
        return redirect(solutionId);
    }

    @DeleteMapping("/reviews/{reviewId}")
    @Login
    public ResponseEntity<?> deleteReview(@PathVariable Long solutionId,@LoggedInMember Long memberId,@PathVariable Long reviewId){
        reviewService.deleteReview(memberId,reviewId);
        // 풀이 상세보기로 리다이렉트
        return redirect(solutionId);
    }
    private ResponseEntity redirect(Long solutionId) {
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(REDIRECT_PATH + "/" + solutionId)).build();
    }

}
