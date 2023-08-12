package com.prograngers.backend.repository.problem;

import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.QProblem;
import com.prograngers.backend.entity.QSolution;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.repository.problem.dto.ProblemResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.prograngers.backend.entity.QProblem.*;
import static com.prograngers.backend.entity.QSolution.*;

@RequiredArgsConstructor
@Repository
@Slf4j
public class QueryDslProblemRepositoryImpl implements QueryDslProblemRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public List<ProblemResponse> searchByAlgorithmAndDataStructureOrderByDateDesc(
            int page, DataStructureConstant dataStructure, AlgorithmConstant algorithm, String orderBy){

        QProblem subProblem = new QProblem("subProblem");
        List<Problem> results = jpaQueryFactory
                .select(problem)
                .from(problem, solution)
                .join(solution.problem, subProblem) // alias가 겹치기 때문
                .where(dataStructureEq(dataStructure),algorithmEq(algorithm))
                .fetch();

        List<ProblemResponse> problemResponses = new ArrayList<>();

        for (Problem result : results ){
            ProblemResponse problemResponse = ProblemResponse.builder()
                    .title(result.getTitle())
                    .ojName(result.getOjName())
                    .build();
            problemResponses.add(problemResponse);
        }

        return problemResponses;
    }


    private BooleanExpression dataStructureEq(DataStructureConstant dataStructure) {
        return dataStructure!=null? solution.dataStructure.eq(dataStructure) : null;
    }
    private BooleanExpression algorithmEq(AlgorithmConstant algorithm) {
        return algorithm!=null? solution.algorithm.eq(algorithm):null;
    }

}
