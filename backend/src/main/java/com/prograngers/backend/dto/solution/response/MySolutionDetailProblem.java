package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.problem.JudgeConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MySolutionDetailProblem {
    private String problemTitle;
    private JudgeConstant ojName;
    private List<Object> tags;
}
