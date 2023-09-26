package com.prograngers.backend.dto.solution.response;

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
public class ShowMySolutionDetailResponse {
    // 문제
    private ProblemResponse problem;
    // 풀이
    private MainSolutionResponse solution;
    // 댓글
    private List<CommentWithRepliesResponse> comments;
    // 추천 풀이
    private List<RecommendedSolutionResponse> recommendedSolutions;
    // 사이드바 이 문제에 대한 풀이 3가지, 스크랩한 풀이 3가지
    private List<SolutionTitleAndIdResponse> mySolutionList;
    private List<SolutionTitleAndIdResponse> myScrapSolutionList;
    public static ShowMySolutionDetailResponse from(
            ProblemResponse problem, MainSolutionResponse solution, List<CommentWithRepliesResponse> comments,
            List<RecommendedSolutionResponse> recommendedSolutions,
            List<SolutionTitleAndIdResponse> solutions, List<SolutionTitleAndIdResponse> scraps){
        return ShowMySolutionDetailResponse.builder()
                .problem(problem)
                .solution(solution)
                .comments(comments)
                .mySolutionList(solutions)
                .myScrapSolutionList(scraps)
                .recommendedSolutions(recommendedSolutions)
                .build();
    }
}
