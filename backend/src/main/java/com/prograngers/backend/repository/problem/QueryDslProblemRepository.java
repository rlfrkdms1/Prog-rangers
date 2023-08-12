package com.prograngers.backend.repository.problem;

import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.repository.problem.dto.ProblemResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface QueryDslProblemRepository {
    public List<ProblemResponse> searchByAlgorithmAndDataStructureOrderByDateDesc(
            int page, DataStructureConstant dataStructure, AlgorithmConstant algorithm, String orderBy);
}
