package com.prograngers.backend.repository.solution;

import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.LanguageConstant;
import com.prograngers.backend.entity.constants.SortConstant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryDslSolutionRepository {

    PageImpl<Solution> getSolutionList(
            Pageable pageable, Long problemId, LanguageConstant language, AlgorithmConstant algorithm,
            DataStructureConstant dataStructure, SortConstant sortBy);
    List<Solution> findProfileSolutions(Long memberId,Long page);
}
