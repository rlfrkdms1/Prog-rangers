package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.entity.Solution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolutionDetailResponse {
    private SolutionDetailSolution solution;
    private List<SolutionDetailComment> comments;

    private static final String SCRAP_PATH = "http://localhost:8080/solutions/";

    public static SolutionDetailResponse toEntity(Solution solution, List<Comment> comments) {


        SolutionDetailResponse response = new SolutionDetailResponse();


        // 스크랩 한 풀이면 스크랩 한 풀이의 링크 찾기
        String scrapLink = null;
        if (solution.getScrapSolution() != null) {
            scrapLink = SCRAP_PATH + solution.getScrapSolution().getId();
        }

        SolutionDetailSolution responseSolution = new SolutionDetailSolution(
                solution.getId(),
                solution.getMember().getNickname(),
                solution.getTitle(),
                solution.getProblem().getLink(),
                solution.getAlgorithm(),
                solution.getDataStructure(),
                solution.getCode(),
                solution.getDescription(),
                solution.getLikes().size(),
                solution.getScraps(),
                scrapLink
        );

        List<SolutionDetailComment> commentResponseList = new ArrayList<>();

        for (Comment comment : comments) {
            commentResponseList.add(
                    new SolutionDetailComment(
                            comment.getMember().getNickname(),
                            comment.getContent(),
                            comment.getMention()
                    )
            );
        }

        response.comments = commentResponseList;
        response.solution = responseSolution;

        return response;
    }
}
