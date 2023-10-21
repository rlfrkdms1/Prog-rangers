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
    private String authorImageUrl;
    private String authorNickname;

    public static SolutionWithProblemResponse from(Solution solution) {
        Problem problem = solution.getProblem();
        Member author = solution.getMember();
        SolutionWithProblemResponse response = SolutionWithProblemResponse.builder()
                .solutionId(solution.getId())
                .solutionTitle(solution.getTitle())
                .language(solution.getLanguage().getView())
                .problemTitle(problem.getTitle())
                .ojName(problem.getOjName())
                .authorImageUrl(author.getPhoto())
                .authorNickname(author.getNickname())
                .algorithm(solution.getAlgorithmView())
                .dataStructure(solution.getDataStructureView())
                .build();

        return response;
    }

    private void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    private void setDataStructure(String dataStructure) {
        this.dataStructure = dataStructure;
    }
}
