package com.prograngers.backend.repository.problem;

import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;

import java.util.List;

public interface QueryDslProblemRepository {
    public List<Problem> findAll(
            int page, DataStructureConstant dataStructure, AlgorithmConstant algorithm, String orderBy);

}
