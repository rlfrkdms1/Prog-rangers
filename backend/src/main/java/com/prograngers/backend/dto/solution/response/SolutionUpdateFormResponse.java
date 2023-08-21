package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolutionUpdateFormResponse {

    private String title;
    private String link;
    private AlgorithmConstant algorithmName;
    private DataStructureConstant dataStructureName;
    private String code;
    private String description;
    private int  level;

    public static SolutionUpdateFormResponse toDto(Solution target) {
        return SolutionUpdateFormResponse.builder()
                .title(target.getTitle())
                .link(target.getProblem()
                        .getLink())
                .algorithmName(target.getAlgorithm())
                .dataStructureName(target.getDataStructure())
                .code(target.getCode())
                .level(target.getLevel())
                .description(target.getDescription()).build();
    }
}
