package com.prograngers.backend.repository.solution;

import com.prograngers.backend.entity.QSolution;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.LanguageConstant;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.prograngers.backend.entity.QSolution.*;

@RequiredArgsConstructor
@Repository
@Slf4j
public class QueryDslSolutionRepositoryImpl implements QueryDslSolutionRepository{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Solution> getSolutionList(int page, Long problemId, LanguageConstant language, AlgorithmConstant algorithm, DataStructureConstant dataStructure, String sortBy) {
        List<Solution> result = jpaQueryFactory
                .selectFrom(solution)
                .where(solution.problem.id.eq(problemId),languageEq(language), algorithmEq(algorithm), dataStructureEq(dataStructure))
                .orderBy(sortByWhat(sortBy))
                .offset((page-1)*4)
                .limit(4)
                .fetch();
        return result;
    }

    private OrderSpecifier<?> sortByWhat(String sortBy) {
        if (sortBy.equals("newest")){
            return solution.date.desc();
        }
        if (sortBy.equals("likes")){
            return solution.likes.size().desc();
        }
        if (sortBy.equals("scraps")){
            return solution.scraps.desc();
        }
        else return  null;
    }

    private BooleanExpression dataStructureEq(DataStructureConstant dataStructure) {
        return dataStructure!=null?solution.dataStructure.eq(dataStructure):null;
    }

    private BooleanExpression algorithmEq(AlgorithmConstant algorithm) {
        return algorithm!=null?solution.algorithm.eq(algorithm):null;
    }

    private BooleanExpression languageEq(LanguageConstant language) {
        return language!=null?solution.language.eq(language):null;
    }
}
