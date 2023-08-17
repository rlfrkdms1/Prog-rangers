package com.prograngers.backend.dto.problem.response;

import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.JudgeConstant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Builder
public class ProblemResponse {
    String title;
    JudgeConstant ojName;

    List<Object> tags = new ArrayList<>();


    public static ProblemResponse from(Problem problem) {
        ProblemResponse problemResponse = ProblemResponse.builder()
                .title(problem.getTitle())
                .ojName(problem.getOjName())
                .tags(new ArrayList<>())
                .build();

        return problemResponse;
    }
}
