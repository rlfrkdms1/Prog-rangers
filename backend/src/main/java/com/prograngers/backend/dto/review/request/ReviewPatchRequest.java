package com.prograngers.backend.dto.review.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewPatchRequest {
    @NotBlank
    String  content;

}
