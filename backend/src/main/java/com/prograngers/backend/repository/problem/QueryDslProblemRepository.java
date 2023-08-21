package com.prograngers.backend.repository.problem;

import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.SortConstant;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryDslProblemRepository {
    public PageImpl<Problem> findAll(
            Pageable pageable, DataStructureConstant dataStructure, AlgorithmConstant algorithm, SortConstant orderBy);

}
