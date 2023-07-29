package com.prograngers.backend.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SolutionDetailResponseSolution {
    Long id;
    String nickname;
    String title;
    String link;
    String algorithmName;
    String dataStructureName;
    String code;
    String description;
    Integer likes;
    Integer scraps;
    String scrapLink;
}
