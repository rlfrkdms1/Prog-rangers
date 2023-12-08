package com.prograngers.backend.dto.problem.response;

import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.problem.Problem;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProblemListResponse {

    private final Long id;
    private final String title;
    private final JudgeConstant ojName;
    private final List<Object> tags;

    public static ProblemListResponse from(Problem problem) {
        return ProblemListResponse.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .ojName(problem.getOjName())
                .tags(new ArrayList<>())
                .build();
    }
}
