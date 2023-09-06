package com.prograngers.backend.repository.problem;

import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.sortconstant.SortConstant;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface QueryDslProblemRepository {
    public PageImpl<Problem> findAll(
            Pageable pageable, DataStructureConstant dataStructure, AlgorithmConstant algorithm, SortConstant orderBy);

}
