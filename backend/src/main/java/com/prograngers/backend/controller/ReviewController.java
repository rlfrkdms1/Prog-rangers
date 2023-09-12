package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.review.request.ReviewPatchRequest;
import com.prograngers.backend.dto.review.request.ReviewPostRequest;
import com.prograngers.backend.dto.review.response.SolutionReviewsResponse;
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
@RequestMapping("prog-rangers/solutions/{solutionId}")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final String REDIRECT_PATH = "http://localhost:8080/prog-rangers/solutions";

    // 상세보기 한 줄 리뷰
    @GetMapping("/reviews")
    public ResponseEntity<?> solutionReviews(@PathVariable Long solutionId){
        SolutionReviewsResponse reviewDetail = reviewService.getReviewDetail(solutionId);
        return ResponseEntity.ok().body(reviewDetail);
    }

    @PostMapping("/reviews")
    @Login
    public ResponseEntity<?> writeReview(@PathVariable Long solutionId, @Valid  @RequestBody ReviewPostRequest reviewPostRequest, @LoggedInMember Long memberId){
        reviewService.writeReview(reviewPostRequest,memberId,solutionId);
        // 풀이 상세보기로 리다이렉트
        return redirect(solutionId);
    }

    @PatchMapping("/reviews/{reviewId}")
    @Login
    public ResponseEntity<?> updateReview(@PathVariable Long solutionId, @Valid  @RequestBody ReviewPatchRequest reviewPatchRequest,
                                         @LoggedInMember Long memberId,@PathVariable Long reviewId){
        reviewService.updateReview(reviewPatchRequest,memberId,reviewId);
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
    private ResponseEntity<Object> redirect(Long solutionId) {
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(REDIRECT_PATH + "/" + solutionId)).build();
    }

}
