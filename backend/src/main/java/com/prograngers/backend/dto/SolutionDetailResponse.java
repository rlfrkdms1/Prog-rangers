package com.prograngers.backend.dto;

import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.entity.Solution;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolutionDetailResponse {
    private SolutionDetailResponseSolution solution;
    private List<SolutionDetailResponseComment> comments;

    public static SolutionDetailResponse toEntity(Solution solution, List<Comment> comments) {
        SolutionDetailResponse response = new SolutionDetailResponse();


        // 스크랩 한 풀이면 스크랩 한 풀이의 링크 찾기
        String scrapLink = null;
        if (solution.getScrapId()!=null){
            scrapLink = "http://localhost:8080/solutions/"+solution.getScrapId().getId();
        }

        SolutionDetailResponseSolution responseSolution = new SolutionDetailResponseSolution(
                solution.getId(),
                solution.getMember().getNickname(),
                solution.getTitle(),
                solution.getProblem().getLink(),
                solution.getAlgorithm().getName(),
                solution.getDataStructure().getName(),
                solution.getCode(),
                solution.getDescription(),
                solution.getLikes().size(),
                solution.getScraps(),
                scrapLink
        );

        List<SolutionDetailResponseComment> commentResponseList = new ArrayList<>();

        for (Comment comment : comments){
            commentResponseList.add(
                    new SolutionDetailResponseComment(
                            comment.getMember().getNickname(),
                            comment.getOrderParent(),
                            comment.getOrderChild(),
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
