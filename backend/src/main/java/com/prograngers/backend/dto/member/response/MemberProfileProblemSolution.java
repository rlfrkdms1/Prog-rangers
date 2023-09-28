package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.problem.JudgeConstant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberProfileProblemSolution {

    private String nickname;
    private String problemName;
    private DataStructureConstant dataStructure;
    private AlgorithmConstant algorithm;
    private JudgeConstant ojName;
    private String description;
    private String[] code;

    public static MemberProfileProblemSolution from(Solution solution){
        return MemberProfileProblemSolution.builder()
                .problemName(solution.getProblem().getTitle())
                .dataStructure(solution.getDataStructure())
                .algorithm(solution.getAlgorithm())
                .ojName(solution.getProblem().getOjName())
                .description(solution.getDescription())
                .code(solution.getCode().split("\n"))
                .build();
    }

}
