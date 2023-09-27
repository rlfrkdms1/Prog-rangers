package com.prograngers.backend.dto.solution.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class MainSolutionResponse {
    private String title;
    private List<Object> tags;
    private String description;
    private String[] code;
    private int likes;
    private int  scraps;

    public static MainSolutionResponse from(String title, List<Object> tags, String description, String[] code, int likes, int scraps){
        return MainSolutionResponse.builder()
                .title(title)
                .tags(tags)
                .description(description)
                .code(code)
                .likes(likes)
                .scraps(scraps)
                .build();
    }
}
