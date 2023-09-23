package com.prograngers.backend.dto.review.request;

import com.prograngers.backend.entity.review.Review;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewPatchRequest {
    @NotBlank(message = "내용은 빈칸일 수 없습니다")
    String  content;

}
