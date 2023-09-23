package com.prograngers.backend.repository.solution;

import com.prograngers.backend.entity.solution.QSolution;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.entity.sortconstant.SortConstant;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.prograngers.backend.entity.QLikes.*;
import static com.prograngers.backend.entity.sortconstant.SortConstant.*;
import static com.prograngers.backend.entity.solution.QSolution.*;

@RequiredArgsConstructor
@Repository
@Slf4j
public class QueryDslSolutionRepositoryImpl implements QueryDslSolutionRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PageImpl<Solution> getSolutionList(
            Pageable pageable, Long problemId, LanguageConstant language,
            AlgorithmConstant algorithm, DataStructureConstant dataStructure, SortConstant sortBy) {
        if (sortBy.equals(NEWEST)){
            return getSolutionsSorByNewest(pageable, problemId, language, algorithm, dataStructure);
        }
        if (sortBy.equals(LIKES)){
            return getSolutionsSortByLikes(pageable, problemId, language, algorithm, dataStructure);
        }
        if (sortBy.equals(SCRAPS)){
            return getSolutionsSortByScraps(pageable, problemId, language, algorithm, dataStructure);
        }
        return null;
    }
    @Override
    public List<Solution> findProfileSolutions(Long memberId,Long page) {
        return jpaQueryFactory
                .select(solution)
                .from(solution)
                .where(solution.member.id.eq(memberId), solution.id.loe(page))
                .orderBy(solution.createdAt.desc())
                .limit(3)
                .fetch();
    }

    @Override
    public List<Solution> findAllSolutionOfNewestProblem(Long memberId){
        QSolution subSolution = new QSolution("subSolution");
        return jpaQueryFactory
                .select(solution)
                .from(solution)
                .where(solution.problem.id.in(
                        JPAExpressions
                                .select(subSolution.problem.id)
                                .from(subSolution)
                                .where(subSolution.member.id.eq(memberId))
                                .orderBy(subSolution.createdAt.desc())
                                .limit(1)
                ))
                .orderBy(solution.createdAt.desc())
                .fetch();
    }
    private BooleanExpression dataStructureEq(DataStructureConstant dataStructure) {
        return dataStructure != null ? solution.dataStructure.eq(dataStructure) : null;
    }

    private BooleanExpression algorithmEq(AlgorithmConstant algorithm) {
        return algorithm != null ? solution.algorithm.eq(algorithm) : null;
    }

    private BooleanExpression languageEq(LanguageConstant language) {
        return language != null ? solution.language.eq(language) : null;
    }

    private static BooleanExpression solutionEqProblemId(Long problemId) {
        return solution.problem.id.eq(problemId);
    }

    private static BooleanExpression solutionPublic() {
        return solution.isPublic.eq(true);
    }

    private List<Solution> getScrapsSolutions(Pageable pageable, Long problemId, LanguageConstant language, AlgorithmConstant algorithm, DataStructureConstant dataStructure) {
        List<Solution> result;
        QSolution subSolution = new QSolution("subSolution");
        result = jpaQueryFactory
                .select(solution)
                .from(subSolution)
                .rightJoin(subSolution.scrapSolution,solution)
                .where(solutionPublic(), solutionEqProblemId(problemId), languageEq(language), algorithmEq(algorithm), dataStructureEq(dataStructure))
                .groupBy(solution.id)
                .orderBy(subSolution.count().desc(),solution.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return result;
    }

    private List<Solution> getLikesSolutions(Pageable pageable, Long problemId, LanguageConstant language, AlgorithmConstant algorithm, DataStructureConstant dataStructure) {
        List<Solution> result;
        result = jpaQueryFactory
                .select(solution)
                .from(likes)
                .rightJoin(likes.solution, solution)
                .where(solutionPublic(), solutionEqProblemId(problemId), languageEq(language), algorithmEq(algorithm), dataStructureEq(dataStructure))
                .groupBy(solution.id)
                .orderBy(likes.id.count().desc(),solution.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return result;
    }

    private List<Solution> getNewestSolutions(Pageable pageable, Long problemId, LanguageConstant language, AlgorithmConstant algorithm, DataStructureConstant dataStructure) {
        List<Solution> result;
        result = jpaQueryFactory
                .selectFrom(solution)
                .where(solutionPublic(), solutionEqProblemId(problemId), languageEq(language), algorithmEq(algorithm), dataStructureEq(dataStructure))
                .orderBy(solution.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return result;
    }

    private Long getCount(Long problemId, LanguageConstant language, AlgorithmConstant algorithm, DataStructureConstant dataStructure) {
        Long count = jpaQueryFactory
                .select(solution.count())
                .from(solution)
                .where(solutionPublic(), solutionEqProblemId(problemId), languageEq(language), algorithmEq(algorithm), dataStructureEq(dataStructure))
                .fetchOne();
        return count;
    }
    private PageImpl<Solution> getSolutionsSortByScraps(Pageable pageable, Long problemId, LanguageConstant language, AlgorithmConstant algorithm, DataStructureConstant dataStructure) {
        List<Solution> result;
        result = getScrapsSolutions(pageable, problemId, language, algorithm, dataStructure);
        Long count = getCount(problemId, language, algorithm, dataStructure);
        PageImpl<Solution> solutions = new PageImpl<>(result, pageable, count);
        return solutions;
    }

    private PageImpl<Solution> getSolutionsSortByLikes(Pageable pageable, Long problemId, LanguageConstant language, AlgorithmConstant algorithm, DataStructureConstant dataStructure) {
        List<Solution> result;
        result = getLikesSolutions(pageable, problemId, language, algorithm, dataStructure);
        Long count = getCount(problemId, language, algorithm, dataStructure);
        PageImpl<Solution> solutions = new PageImpl<>(result, pageable, count);
        return solutions;
    }

    private PageImpl<Solution> getSolutionsSorByNewest(Pageable pageable, Long problemId, LanguageConstant language, AlgorithmConstant algorithm, DataStructureConstant dataStructure) {
        List<Solution> result;
        result = getNewestSolutions(pageable, problemId, language, algorithm, dataStructure);
        Long count = getCount(problemId, language, algorithm, dataStructure);
        PageImpl<Solution> solutions = new PageImpl<>(result, pageable, count);
        return solutions;
    }
}
