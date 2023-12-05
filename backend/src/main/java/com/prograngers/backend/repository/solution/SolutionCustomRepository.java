package com.prograngers.backend.repository.solution;

import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.sortconstant.SortConstant;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface SolutionCustomRepository {

    PageImpl<Solution> getSolutionList(
            Pageable pageable, Long problemId, LanguageConstant language, AlgorithmConstant algorithm,
            DataStructureConstant dataStructure, SortConstant sortBy);

    List<Solution> findProfileSolutions(Long memberId, Long page);

    Page<Solution> getMyList(Pageable pageable, String keyword, LanguageConstant language, AlgorithmConstant algorithm,
                             DataStructureConstant dataStructure, Integer level, Long memberId);

    List<Solution> findMyRecentSolutions(Long memberId, int limit);

    List<Solution> findFollowingsRecentSolutions(Long memberId, int limit);

    List<Solution> findTopLimitsSolutionOfProblemOrderByLikesDesc(Problem problem, int limit);

    Solution findOneRecentSolutionByMemberId(Long memberId);
}
