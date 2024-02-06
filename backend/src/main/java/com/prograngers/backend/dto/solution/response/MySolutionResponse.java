package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.solution.Solution;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MySolutionResponse {

    private String title;
    private String algorithm;
    private String dataStructure;
    private String description;
    private String[] code;
    private Long likes;
    private Long scraps;
    private Integer level;
    private boolean isPublic;

    public static MySolutionResponse from(Solution solution, Long likes, Long scraps) {
        return MySolutionResponse.builder()
                .title(solution.getTitle())
                .dataStructure(solution.getDataStructureName())
                .algorithm(solution.getAlgorithmName())
                .description(solution.getDescription())
                .code(solution.getCode().split(System.lineSeparator()))
                .isPublic(solution.isPublic())
                .likes(likes)
                .scraps(scraps)
                .level(solution.getLevel())
                .build();
    }
}
