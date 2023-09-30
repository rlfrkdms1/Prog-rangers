package com.prograngers.backend.dto.problem.response;

import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.problem.Problem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ProblemListResponse {
    String title;
    JudgeConstant ojName;

    List<Object> tags;


    public static ProblemListResponse from(Problem problem) {
        ProblemListResponse problemListResponseResponse = ProblemListResponse.builder()
                .title(problem.getTitle())
                .ojName(problem.getOjName())
                .tags(new ArrayList<>())
                .build();

        return problemListResponseResponse;
    }
}
