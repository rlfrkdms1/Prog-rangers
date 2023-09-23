package com.prograngers.backend.dto.solution.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
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
