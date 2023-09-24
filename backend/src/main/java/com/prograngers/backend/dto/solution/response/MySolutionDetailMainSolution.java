package com.prograngers.backend.dto.solution.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class MySolutionDetailMainSolution {
    private String title;
    private List<Object> tags;
    private String description;
    private String[] code;
    private int likes;
    private int  scraps;

    public static MySolutionDetailMainSolution from(String title, List<Object> tags, String description, String[] code, int likes, int scraps){
        return MySolutionDetailMainSolution.builder()
                .title(title)
                .tags(tags)
                .description(description)
                .code(code)
                .likes(likes)
                .scraps(scraps)
                .build();
    }
}
