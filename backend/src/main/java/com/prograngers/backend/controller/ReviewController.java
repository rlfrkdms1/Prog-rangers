package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.review.request.UpdateReviewRequest;
import com.prograngers.backend.dto.review.request.WriteReviewRequest;
import com.prograngers.backend.dto.review.response.ShowReviewsResponse;
import com.prograngers.backend.service.ReviewService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/solutions/{solutionId}/reviews")
    public ResponseEntity<?> solutionReviews(@PathVariable Long solutionId,
                                             @LoggedInMember(required = false) Long memberId) {
        ShowReviewsResponse reviewDetail = reviewService.getReviewDetail(solutionId, memberId);
        return ResponseEntity.ok().body(reviewDetail);
    }

    @Login
    @PostMapping("/solutions/{solutionId}/reviews")
    public ResponseEntity<Void> write(@PathVariable Long solutionId, @Valid @RequestBody WriteReviewRequest request,
                                      @LoggedInMember Long memberId) {
        reviewService.writeReview(request, memberId, solutionId);
        return ResponseEntity.created(URI.create("/api/v1/solutions/" + solutionId)).build();
    }

    @Login
    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> update(@Valid @RequestBody UpdateReviewRequest request,
                                       @LoggedInMember Long memberId, @PathVariable Long reviewId) {
        reviewService.updateReview(request, memberId, reviewId);
        return ResponseEntity.noContent().build();
    }

    @Login
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> delete(@LoggedInMember Long memberId, @PathVariable Long reviewId) {
        reviewService.deleteReview(memberId, reviewId);
        return ResponseEntity.noContent().build();
    }

}
