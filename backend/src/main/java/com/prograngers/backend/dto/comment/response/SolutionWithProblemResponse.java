package com.prograngers.backend.dto.comment.response;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SolutionWithProblemResponse {

    private Long solutionId;
    private String solutionTitle;
    private String algorithm;
    private String dataStructure;
    private String problemTitle;
    private String ojName;
    private String authorImageUrl;
    private String authorNickname;

    public static SolutionWithProblemResponse from(Solution solution) {
        Problem problem = solution.getProblem();
        Member author = solution.getMember();
        return new SolutionWithProblemResponse(solution.getId(), solution.getTitle(), solution.getAlgorithm().getStringValue(), solution.getDataStructure().getStringValue(),
                problem.getTitle(), problem.getOjName().name(), author.getPhoto(), author.getNickname());
    }
}
