package com.prograngers.backend.dto.review.request;

import com.prograngers.backend.entity.review.Review;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewPatchRequest {
    @NotBlank
    String  content;

    public Review updateReview(Review targetReview) {
        return targetReview.update(content);
    }
}
