package com.prograngers.backend.dto.solution.response;

import lombok.Builder;
@Builder
public class MySolutionDetailSolution {
    private String title;
    private Long id;

    public static MySolutionDetailSolution from(String title, Long id) {
        return MySolutionDetailSolution.builder()
                .title(title)
                .id(id)
                .build();
    }
}
