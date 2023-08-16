package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SolutionResponse {
    private String solutionName;
    private AlgorithmConstant algorithm;
    private DataStructureConstant dataStructure;
}
