package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class SolutionDetailResponse {

    private SolutionDetailProblem problem;
    private SolutionDetailSolution solution;
    private List<SolutionDetailComment> comments;
    private boolean isMine;
    private static final String LOCAL_SCRAP_PATH = "http://localhost:8080/prog-rangers/solutions/";

    public static SolutionDetailResponse from(Solution solution, List<Comment> comments,
                                                  boolean scraped, int scrapCount, boolean pushedLike, int likeCount, boolean isMine) {
        return new SolutionDetailResponse(
                SolutionDetailProblem.from(solution.getProblem()),
                makeResponseSolution(solution, scraped, scrapCount, pushedLike, likeCount, getScrapLink(solution)),
                makeCommentsResponse(comments),
                isMine
        );
    }

    private static List<SolutionDetailComment> makeCommentsResponse(List<Comment> comments) {
        List<SolutionDetailComment> commentResponseList = new ArrayList<>();
        // 먼저 부모가 없는 댓글들을 전부 더한다
        comments.stream().filter(comment -> comment.getParentId()==null)
                .forEach(comment-> commentResponseList.add(SolutionDetailComment.from(comment,new ArrayList<>())));
        // 부모가 있는 댓글들을 더한다
        comments.stream().filter((comment)->comment.getParentId()!=null)
                .forEach((comment)->{
                    addReplyComments(commentResponseList, comment);
                });

        return commentResponseList;
    }

    private static void addReplyComments(List<SolutionDetailComment> commentResponseList, Comment comment) {
        commentResponseList.stream()
                .filter(parentComment->parentComment.getId().equals(comment.getParentId()))
                .findFirst()
                .get()
                .getReplies()
                .add(SolutionDetailComment.from(comment));
    }

    private static SolutionDetailSolution makeResponseSolution(Solution solution, boolean scraped, int scrapCount, boolean pushedLike, int likeCount, String scrapLink) {
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
        return responseSolution;
    }

    private static String getScrapLink(Solution solution) {
        // 스크랩 한 풀이면 스크랩 한 풀이의 링크 찾기
        String scrapLink = null;
        if (solution.getScrapSolution() != null) {
            scrapLink = LOCAL_SCRAP_PATH + solution.getScrapSolution().getId();
        }
        return scrapLink;
    }
}
