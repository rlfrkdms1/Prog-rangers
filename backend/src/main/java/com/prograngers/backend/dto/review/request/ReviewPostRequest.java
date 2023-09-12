package com.prograngers.backend.dto.review.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPostRequest {
    @NotNull
    private int codeLineNumber;
    @NotBlank
    private String content;
}
