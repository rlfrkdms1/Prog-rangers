package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SolutionAtSolutionListResponse {
    private Long solutionId;
    private String solutionName;
    private String algorithm;
    private String dataStructure;
}
