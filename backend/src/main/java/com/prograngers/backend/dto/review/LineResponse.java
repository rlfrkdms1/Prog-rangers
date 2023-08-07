package com.prograngers.backend.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class LineResponse {
    private Integer codeLineNumber;
    private String code;
    private List<ReviewResponse> reviews = new ArrayList<>();
}
