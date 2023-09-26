package com.prograngers.backend.dto.solution.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SolutionTitleAndIdResponse {
    private String title;
    private Long id;

    public static SolutionTitleAndIdResponse from(String title, Long id) {
        return SolutionTitleAndIdResponse.builder()
                .title(title)
                .id(id)
                .build();
    }
}
