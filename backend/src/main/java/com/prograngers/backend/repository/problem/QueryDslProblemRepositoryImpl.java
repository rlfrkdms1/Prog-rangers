package com.prograngers.backend.repository.problem;

import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.repository.problem.dto.ProblemResponse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static com.prograngers.backend.entity.QProblem.*;
import static com.prograngers.backend.entity.QSolution.*;

@RequiredArgsConstructor
@Repository
@Slf4j
public class QueryDslProblemRepositoryImpl implements QueryDslProblemRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ProblemResponse> searchByAlgorithmAndDataStructureOrderByDateDesc(
            int page, DataStructureConstant dataStructure, AlgorithmConstant algorithm, String orderBy) {

//        QProblem subProblem = new QProblem("subProblem");
//        QSolution subSolution = new QSolution("subSolution");

        // 양방향 연관관계로 변경
        List<Problem> results = jpaQueryFactory
                .selectFrom(problem)
                // solution을 조회해서 자료구조, 알고리즘을 알아내야 해서 성능을 위해 패치조인
                .join(problem.solutions, solution).fetchJoin()
                .where(dataStructureEq(dataStructure), algorithmEq(algorithm))
                .orderBy(orderByWhat(orderBy))
                .offset((page-1)*4)
                .limit(4)
                .fetch();

        // 반환할 dto 리스트
        List<ProblemResponse> problemResponses = new ArrayList<>();

        // 결과를  for문 돌면서 반환 dto를 만든다
        for (Problem result : results) {
            ProblemResponse problemResponse = ProblemResponse.builder()
                    .title(result.getTitle())
                    .ojName(result.getOjName())
                    .tags(new ArrayList<>())
                    .build();

            List<Solution> solutions = result.getSolutions();
            HashMap<Object,Integer> hm = new HashMap<>();

            for (Solution solution : solutions) {
                hm.put(solution.getAlgorithm(),hm.getOrDefault(solution.getAlgorithm(),1)+1);
                hm.put(solution.getDataStructure(),hm.getOrDefault(solution.getDataStructure(),1)+1);
            }

            List<Object> keySet = new ArrayList<>(hm.keySet());
            keySet.sort((num1,num2)->hm.get(num2).compareTo(hm.get(num1)));

            for (int i=0; i<keySet.size(); i++){
                if (i==3) break;
                Object tag = keySet.get(i);
                if (tag!=null){
                    problemResponse.getTags().add(keySet.get(i));
                }
            }


            problemResponses.add(problemResponse);
        }

        return problemResponses;
    }

    private OrderSpecifier<?> orderByWhat(String orderBy) {
        if (orderBy.equals("date")){ // date 인 경우
            log.info("orderByDate");
           return problem.date.desc();
            // size-1의 solution (제일 마지막 solution)의 날짜  기준으로 정렬
//            NumberExpression<Integer> size = problem.solutions.size().subtract(1);
//           return problem.solutions.get(size).date.desc();
        } else { // solution 개수인 경우
            // solution 개수에 따라 내림차순으로 정렬
            log.info("orderBySolutionCount");
            return problem.solutions.size().desc();
        }
    }

    private BooleanExpression dataStructureEq(DataStructureConstant dataStructure) {
        return dataStructure != null ? solution.dataStructure.eq(dataStructure) : null;
    }

    private BooleanExpression algorithmEq(AlgorithmConstant algorithm) {
        return algorithm != null ? solution.algorithm.eq(algorithm) : null;
    }

}
