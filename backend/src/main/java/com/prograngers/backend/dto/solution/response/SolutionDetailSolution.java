package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolutionDetailSolution {
    private Long id;
    private String nickname;
    private String title;
    private String link;
    private AlgorithmConstant algorithmName;
    private DataStructureConstant dataStructureName;
    private String[] code;
    private String description;
    private Integer likes;
    private Integer scraps;
    private String scrapLink;
    private boolean pushedLike;
    private boolean scraped;
    private boolean mine;

    public static SolutionDetailSolution from(Solution solution, String nickname, String problemLink,
                                              int likeCount, int scrapCount, boolean pushedLike, boolean scraped, boolean mine, String scrapSolutionLink){
        return SolutionDetailSolution.builder()
                .id(solution.getId())
                .nickname(nickname)
                .title(solution.getTitle())
                .link(problemLink)
                .algorithmName(solution.getAlgorithm())
                .dataStructureName(solution.getDataStructure())
                .code(solution.getCode().split("\n"))
                .description(solution.getDescription())
                .likes(likeCount)
                .scraps(scrapCount)
                .pushedLike(pushedLike)
                .scraped(scraped)
                .mine(mine)
                .scrapLink(scrapSolutionLink)
                .build();
    }
}
