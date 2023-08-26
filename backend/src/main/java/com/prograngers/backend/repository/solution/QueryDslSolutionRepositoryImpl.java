package com.prograngers.backend.repository.solution;

import com.prograngers.backend.entity.solution.QSolution;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.entity.constants.SortConstant;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.prograngers.backend.entity.QLikes.*;
import static com.prograngers.backend.entity.constants.SortConstant.*;
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
        List<Solution> result = null;
        if (sortBy.equals(NEWEST)){
            result = jpaQueryFactory
                    .selectFrom(solution)
                    .where(solution.problem.id.eq(problemId), languageEq(language), algorithmEq(algorithm), dataStructureEq(dataStructure))
                    .orderBy(solution.createdDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        } else if (sortBy.equals(LIKES)){
            result = jpaQueryFactory
                    .select(solution)
                    .from(likes)
                    .rightJoin(likes.solution, solution)
                    .where(solution.problem.id.eq(problemId), languageEq(language), algorithmEq(algorithm), dataStructureEq(dataStructure))
                    .groupBy(solution.id)
                    .orderBy(likes.id.count().desc(),solution.createdDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        } else if (sortBy.equals(SCRAPS)){
            QSolution subSolution = new QSolution("subSolution");
            result = jpaQueryFactory
                    .select(solution)
                    .from(subSolution)
                    .rightJoin(subSolution.scrapSolution,solution)
                    .where(solution.problem.id.eq(problemId), languageEq(language), algorithmEq(algorithm), dataStructureEq(dataStructure))
                    .groupBy(solution.id)
                    .orderBy(subSolution.count().desc(),solution.createdDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }
        Long count = jpaQueryFactory
                .select(solution.count())
                .from(solution)
                .where(solution.problem.id.eq(problemId), languageEq(language), algorithmEq(algorithm), dataStructureEq(dataStructure))
                .fetchOne();
        PageImpl<Solution> solutions = new PageImpl<>(result, pageable, count);
        return solutions;
    }

    @Override
    public List<Solution> findProfileSolutions(Long memberId,Long page) {
        return jpaQueryFactory
                .select(solution)
                .from(solution)
                .where(solution.member.id.eq(memberId), solution.id.loe(page))
                .orderBy(solution.createdDate.desc())
                .limit(3)
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
}
