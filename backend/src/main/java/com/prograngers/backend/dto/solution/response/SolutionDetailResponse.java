package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.entity.Problem;
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

    private SolutionDetailProblem problem;
    private SolutionDetailSolution solution;
    private List<SolutionDetailComment> comments;

    private boolean isMine;
    private static final String SCRAP_PATH = "http://localhost:8080/prog-rangers/solutions/";

    public static SolutionDetailResponse from(Problem problem, Solution solution, List<Comment> comments,
                                                  boolean scraped, int scrapCount,
                                                  boolean pushedLike, int likeCount,
                                                  boolean isMine
    ) {


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
                likeCount,
                scrapCount,
                scrapLink,
                pushedLike,
                scraped
        );

        List<SolutionDetailComment> commentResponseList = new ArrayList<>();

        // 먼저 부모가 없는 댓글들을 전부 더한다
        for (Comment comment : comments) {
            if (comment.getParentId()==null){
                commentResponseList.add(
                        new SolutionDetailComment(
                                comment.getMember().getPhoto(),
                                comment.getId(),
                                comment.getMember().getNickname(),
                                comment.getContent(),
                                comment.getMention(),
                                new ArrayList<>()
                        )
                );
            }
        }

        // 부모가 있는 댓글들을 더한다
        for (Comment comment : comments){
            Long parentId = comment.getParentId();
            if (parentId!=null){
                for (SolutionDetailComment parentComment : commentResponseList){
                    if (parentComment.getId().equals(parentId)){
                        parentComment.getReplies().add(
                                new SolutionDetailComment(
                                        comment.getMember().getPhoto(),
                                        comment.getId(),
                                        comment.getMember().getNickname(),
                                        comment.getContent(),
                                        comment.getMention(),
                                        null
                                )
                        );
                    }
                }
            }
        }

        response.comments = commentResponseList;
        response.solution = responseSolution;
        response.isMine = isMine;
        response.problem= SolutionDetailProblem.from(problem);

        return response;
    }
}
