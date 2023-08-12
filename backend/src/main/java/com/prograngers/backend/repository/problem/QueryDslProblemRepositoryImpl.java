package com.prograngers.backend.repository.problem;

import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.repository.problem.dto.ProblemResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.prograngers.backend.entity.QProblem.*;
import static com.prograngers.backend.entity.QSolution.*;

@RequiredArgsConstructor
@Repository
@Slf4j
public class QueryDslProblemRepositoryImpl implements QueryDslProblemRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ProblemResponse> searchByAlgorithmAndDataStructureOrderByDateDesc(
            int page, DataStructureConstant dataStructure, AlgorithmConstant algorithm, String orderBy) {

        // 양방향 연관관계로 변경
        List<Problem> results = jpaQueryFactory
                .selectFrom(problem)
                // solution을 조회해서 자료구조, 알고리즘을 알아내야 해서 성능을 위해 패치조인
                .join(problem.solutions, solution).fetchJoin()
                .where(dataStructureEq(dataStructure), algorithmEq(algorithm))
                .fetch();

        List<ProblemResponse> problemResponses = new ArrayList<>();

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
            List<Map.Entry<Object, Integer>> collect = hm.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toList());
            for (Map.Entry<Object, Integer> entry : collect) {
                problemResponse.getTags().add(entry.getKey());
            }

            problemResponses.add(problemResponse);
        }

        return problemResponses;
    }


    private BooleanExpression dataStructureEq(DataStructureConstant dataStructure) {
        return dataStructure != null ? solution.dataStructure.eq(dataStructure) : null;
    }

    private BooleanExpression algorithmEq(AlgorithmConstant algorithm) {
        return algorithm != null ? solution.algorithm.eq(algorithm) : null;
    }

}
