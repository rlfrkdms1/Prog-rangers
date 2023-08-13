package com.prograngers.backend.dto;

import com.prograngers.backend.entity.constants.JudgeConstant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SolutionListResponse {
    String problemName;
    JudgeConstant ojName;
    List<SolutionResponse> solutions;
}
