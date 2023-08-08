package com.prograngers.backend.dto.problem;

import com.prograngers.backend.entity.constants.AlgorithmConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlgorithmAndCount {
    AlgorithmConstant algorithm;
    Integer count;
}
