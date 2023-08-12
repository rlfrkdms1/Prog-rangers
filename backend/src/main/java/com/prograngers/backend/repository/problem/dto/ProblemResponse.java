package com.prograngers.backend.repository.problem.dto;

import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.JudgeConstant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ProblemResponse {
    String title;
    JudgeConstant ojName;
    List<AlgorithmConstant> algorithms = new ArrayList<>();
    List<DataStructureConstant> dataStructures = new ArrayList<>();
}
