package com.prograngers.backend.dto.review;

import com.prograngers.backend.entity.constants.AlgorithmConstant;

import java.util.ArrayList;
import java.util.List;

public class SolutionReviewsResponse {
    private String title;
    private AlgorithmConstant algorithm;
    private String solution;
    private List<Line> lines = new ArrayList<>();
}
