package com.prograngers.backend.dto.problem;

import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.JudgeConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemAlgorithmDataStructureResponse {
    String title;
    JudgeConstant ojName;
    List<AlgorithmConstant> algorithms = new ArrayList<>();
    List<DataStructureConstant> dataStructures = new ArrayList<>();
}
