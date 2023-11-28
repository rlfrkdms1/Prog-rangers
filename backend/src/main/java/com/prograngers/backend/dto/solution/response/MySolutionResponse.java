package com.prograngers.backend.dto.solution.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MySolutionResponse {

    private String title;
    private List<Object> tags;
    private String description;
    private String[] code;
    private Long likes;
    private Long scraps;

    public static MySolutionResponse from(String title, List<Object> tags, String description, String[] code,
                                          Long likes, Long scraps) {
        return MySolutionResponse.builder()
                .title(title)
                .tags(tags)
                .description(description)
                .code(code)
                .likes(likes)
                .scraps(scraps)
                .build();
    }
}
