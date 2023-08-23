package com.prograngers.backend.repository.problem;

import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.SortConstant;
import com.prograngers.backend.exception.enumtype.SortTypeNotFoundException;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


import static com.prograngers.backend.entity.QProblem.*;
import static com.prograngers.backend.entity.QSolution.*;
import static com.prograngers.backend.entity.constants.SortConstant.*;

@RequiredArgsConstructor
@Repository
@Slf4j
public class QueryDslProblemRepositoryImpl implements QueryDslProblemRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public PageImpl<Problem> findAll(
            Pageable pageable, DataStructureConstant dataStructure, AlgorithmConstant algorithm, SortConstant orderBy) {
        log.info("### problemRepository findAll 호출 ###");

        // 양방향 연관관계로 변경
        List<Problem> results = jpaQueryFactory
                .selectFrom(problem)
                // solution을 조회해서 자료구조, 알고리즘을 알아내야 해서 성능을 위해 패치조인
                .join(problem.solutions, solution).fetchJoin()
                .where(dataStructureEq(dataStructure), algorithmEq(algorithm))
                .orderBy(orderCondition(orderBy))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

         // int size = results.size();
        long size = jpaQueryFactory
                .selectFrom(problem)
                .join(problem.solutions, solution)
                .groupBy(problem)
                .where(dataStructureEq(dataStructure), algorithmEq(algorithm))
                .fetchCount();

        log.info("size : {}",size);

        return new PageImpl<>(results,pageable,size);
    }

    private OrderSpecifier<?> orderCondition(SortConstant orderBy) {
        if (orderBy.equals(NEWEST)) { // date 인 경우
            log.info("orderByDate");
            return problem.id.desc();
            // size-1의 solution (제일 마지막 solution)의 날짜  기준으로 정렬
//            NumberExpression<Integer> size = problem.solutions.size().subtract(1);
//           return problem.solutions.get(size).date.desc();
        } else if (orderBy.equals(SOLUTIONS)){ // solution 개수인 경우
            // solution 개수에 따라 내림차순으로 정렬
            log.info("orderBySolutionCount");
            return problem.solutions.size().desc();
        }
        else throw new SortTypeNotFoundException();
    }

    private BooleanExpression dataStructureEq(DataStructureConstant dataStructure) {
        return dataStructure != null ? solution.dataStructure.eq(dataStructure) : null;
    }

    private BooleanExpression algorithmEq(AlgorithmConstant algorithm) {
        return algorithm != null ? solution.algorithm.eq(algorithm) : null;
    }

}
