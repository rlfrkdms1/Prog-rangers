package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
<<<<<<< HEAD
import com.prograngers.backend.dto.review.response.SolutionReviewsResponse;
=======
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.review.request.UpdateReviewRequest;
import com.prograngers.backend.dto.review.request.WriteReviewRequest;
import com.prograngers.backend.dto.review.response.ShowReviewsResponse;
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
import com.prograngers.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
<<<<<<< HEAD
=======
import java.net.URI;
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

<<<<<<< HEAD
    // 상세보기 한 줄 리뷰
    @GetMapping("/reviews")
    public ResponseEntity<?> solutionReviews(@PathVariable Long solutionId, @LoggedInMember(required = false) Long memberId){
        SolutionReviewsResponse reviewDetail = reviewService.getReviewDetail(solutionId, memberId);
        return ResponseEntity.ok().body(reviewDetail);
    }
=======
    @GetMapping("/solutions/{solutionId}/reviews")
    public ResponseEntity<?> solutionReviews(@PathVariable Long solutionId, @LoggedInMember(required = false) Long memberId) {
        ShowReviewsResponse reviewDetail = reviewService.getReviewDetail(solutionId, memberId);
        return ResponseEntity.ok().body(reviewDetail);
    }

    @Login
    @PostMapping("/solutions/{solutionId}/reviews")
    public ResponseEntity<Void> write(@PathVariable Long solutionId, @Valid @RequestBody WriteReviewRequest request, @LoggedInMember Long memberId) {
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

>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
}
