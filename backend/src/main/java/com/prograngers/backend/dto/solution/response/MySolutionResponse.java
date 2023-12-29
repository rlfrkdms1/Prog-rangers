package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.solution.Solution;
import java.util.Arrays;
import java.util.List;
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
    private boolean isPublic;

    public static MySolutionResponse from(Solution solution, Long likes, Long scraps) {
        return MySolutionResponse.builder()
                .title(solution.getTitle())
                .dataStructure(solution.getDataStructureView())
                .algorithm(solution.getAlgorithmView())
                .description(solution.getDescription())
                .code(solution.getCode().split("\n"))
                .isPublic(solution.isPublic())
                .likes(likes)
                .scraps(scraps)
                .build();
    }
}
