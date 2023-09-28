package com.prograngers.backend.repository.solution;

import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.entity.sortconstant.SortConstant;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryDslSolutionRepository {

    PageImpl<Solution> getSolutionList(
            Pageable pageable, Long problemId, LanguageConstant language, AlgorithmConstant algorithm,
            DataStructureConstant dataStructure, SortConstant sortBy);
    List<Solution> findProfileSolutions(Long memberId,Long page);

    List<Solution> findTop6SolutionOfProblemOrderByLikesDesc(Long problemId);
}
