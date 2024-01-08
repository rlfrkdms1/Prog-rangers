package com.prograngers.backend.dto.comment.response;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SolutionWithProblemResponse {

    private Long solutionId;
    private String solutionTitle;
    private String algorithm;
    private String dataStructure;
    private String language;
    private String problemTitle;
    private JudgeConstant ojName;
    private AuthorResponse author;

    public static SolutionWithProblemResponse from(Solution solution) {
        Problem problem = solution.getProblem();
        Member author = solution.getMember();
        return SolutionWithProblemResponse.builder()
                .solutionId(solution.getId())
                .solutionTitle(solution.getTitle())
                .language(solution.getLanguage().getView())
                .problemTitle(problem.getTitle())
                .ojName(problem.getOjName())
                .author(AuthorResponse.from(author))
                .algorithm(solution.getAlgorithmName())
                .dataStructure(solution.getDataStructureName())
                .build();
    }
}
