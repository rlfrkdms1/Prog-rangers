package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.JudgeConstant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberProfileProblemSolution {
    /**
     * 문제제목, 풀이알고리즘, 풀이 자료구조, 저지명
     * 풀이설명, 소스코드
     */
    private String problemName;
    private DataStructureConstant dataStructure;
    private AlgorithmConstant algorithm;
    private JudgeConstant ojName;

    private String description;

    private String code;

    public static MemberProfileProblemSolution from(Solution solution){
        return MemberProfileProblemSolution.builder()
                .problemName(solution.getProblem().getTitle())
                .dataStructure(solution.getDataStructure())
                .algorithm(solution.getAlgorithm())
                .ojName(solution.getProblem().getOjName())
                .description(solution.getDescription())
                .code(solution.getCode())
                .build();
    }

}
