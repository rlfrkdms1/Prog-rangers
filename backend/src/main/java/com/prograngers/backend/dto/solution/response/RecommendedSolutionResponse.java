package com.prograngers.backend.dto.solution.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendedSolutionResponse {
    private Long id;
    private int likes;
    private String solutionName;
    private String nickname;

    public static RecommendedSolutionResponse from(Long id, int likes, String solutionName, String nickname) {
        return RecommendedSolutionResponse.builder()
                .id(id)
                .likes(likes)
                .solutionName(solutionName)
                .nickname(nickname)
                .build();
    }
}
