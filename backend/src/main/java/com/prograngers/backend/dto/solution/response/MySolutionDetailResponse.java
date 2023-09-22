package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MySolutionDetailResponse {

    // 문제
    private MySolutionDetailProblem problem;
    // 풀이
    private String description;
    private String[] code;
    private int likes;
    private int  scraps;
    // 댓글
    private List<SolutionDetailComment> comments;

    // 추천 풀이
    private List<MySolutionRecommendedSolution> recommendedSolutionTitle;

    // 사이드바 이 문제에 대한 풀이 3가지, 스크랩한 풀이 3가지
    private List<MySolutionDetailSolution> mySolutionList;
    private List<MySolutionDetailSolution> myScrapSolutionList;
    public static MySolutionDetailResponse from(
            MySolutionDetailProblem problem, Solution solution, List<SolutionDetailComment> comments, int likeCount, int scrapCount,
            List<MySolutionDetailSolution> solutions, List<MySolutionDetailSolution> scraps){
        return MySolutionDetailResponse.builder()
                .problem(problem)
                .description(solution.getDescription())
                .code(solution.getCode().split("\n"))
                .likes(likeCount)
                .scraps(scrapCount)
                .comments(comments)
                .mySolutionList(solutions)
                .myScrapSolutionList(scraps)
                .build();
    }
}
