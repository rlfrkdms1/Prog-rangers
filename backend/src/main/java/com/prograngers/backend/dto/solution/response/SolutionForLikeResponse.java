package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SolutionForLikeResponse {

    private Long id;
    private String title;
    private String algorithm;
    private String dataStructure;
    private String language;
    private String author;
    private String[] description;
    private String[] code;

    public static SolutionForLikeResponse from(Solution solution) {
        Member author = solution.getMember();
        SolutionForLikeResponse response = SolutionForLikeResponse.builder()
                .id(solution.getId())
                .title(solution.getTitle())
                .author(author.getNickname())
                .language(solution.getLanguage().getView())
                .description(solution.getDescription().split(System.lineSeparator()))
                .code(solution.getCode().split(System.lineSeparator()))
                .algorithm(solution.getAlgorithmName())
                .dataStructure(solution.getDataStructureName())
                .build();
        return response;
    }

}
