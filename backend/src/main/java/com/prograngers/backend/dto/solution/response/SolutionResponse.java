package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolutionResponse {
    private Long id;
    private String nickname;
    private String title;
    private String link;
    private String algorithm;
    private String dataStructure;
    private String language;
    private String[] code;
    private String description;
    private Integer likes;
    private Integer scraps;
    private Long scrapId;
    private boolean pushedLike;
    private boolean scraped;
    private boolean mine;

    public static SolutionResponse from(Solution solution, String nickname, String problemLink,
                                        int likeCount, int scrapCount, boolean pushedLike, boolean scraped,
                                        boolean mine, Long scrapId) {
        return SolutionResponse.builder()
                .id(solution.getId())
                .nickname(nickname)
                .title(solution.getTitle())
                .link(problemLink)
                .algorithm(solution.getAlgorithmView())
                .dataStructure(solution.getDataStructureView())
                .language(solution.getLanguage().getView())
                .code(solution.getCode().split("\n"))
                .description(solution.getDescription())
                .likes(likeCount)
                .scraps(scrapCount)
                .pushedLike(pushedLike)
                .scraped(scraped)
                .mine(mine)
                .scrapId(scrapId)
                .build();
    }
}
