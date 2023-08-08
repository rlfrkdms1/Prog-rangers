package com.prograngers.backend.dto.problem;

import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataStructureAndCount {
    DataStructureConstant dataStructure;
    Integer count;
}
