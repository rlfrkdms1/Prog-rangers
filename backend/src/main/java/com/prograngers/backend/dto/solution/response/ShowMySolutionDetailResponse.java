package com.prograngers.backend.dto.solution.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowMySolutionDetailResponse {
    private ProblemResponse problem;
    private MySolutionResponse solution;
    private List<CommentWithRepliesResponse> comments;
    private List<ReviewWithRepliesResponse> reviews;
    private List<RecommendedSolutionResponse> recommendedSolutions;
    private List<SolutionTitleAndIdResponse> mySolutionList;
    private List<SolutionTitleAndIdResponse> myScrapSolutionList;

    public static ShowMySolutionDetailResponse of(
            ProblemResponse problem, MySolutionResponse solution, List<CommentWithRepliesResponse> comments,
            List<ReviewWithRepliesResponse> reviews,
            List<RecommendedSolutionResponse> recommendedSolutions,
            List<SolutionTitleAndIdResponse> solutions, List<SolutionTitleAndIdResponse> scraps) {
        return ShowMySolutionDetailResponse.builder()
                .problem(problem)
                .solution(solution)
                .comments(comments)
                .reviews(reviews)
                .mySolutionList(solutions)
                .myScrapSolutionList(scraps)
                .recommendedSolutions(recommendedSolutions)
                .build();
    }
}
