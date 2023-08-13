package com.prograngers.backend.repository.solution;

import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.LanguageConstant;

import java.util.List;

public class QueryDslSolutionRepositoryImpl implements QueryDslSolutionRepository{
    @Override
    public List<Solution> getSolutionList(LanguageConstant language, AlgorithmConstant algorithm, DataStructureConstant dataStructure, String sortBy) {
        return null;
    }
}
