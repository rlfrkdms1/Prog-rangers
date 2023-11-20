package com.prograngers.backend.dto.review.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReviewRequest {
    @NotBlank(message = "내용은 빈칸일 수 없습니다")
    String content;
}
