package com.prograngers.backend.entity.solution;

import static com.prograngers.backend.entity.solution.AlgorithmConstant.DFS;
import static com.prograngers.backend.entity.solution.DataStructureConstant.QUEUE;
import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static com.prograngers.backend.support.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.support.fixture.SolutionFixture.공개_풀이;
import static org.assertj.core.api.Assertions.assertThat;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolutionTest {

    private Solution solution;
    private Member member;
    private Problem problem;

    @BeforeEach
    void beforeEach() {
        member = 장지담.기본_정보_생성();
        problem = 백준_문제.기본_정보_생성();
        solution = 공개_풀이.기본_정보_생성(problem, member, LocalDateTime.now(), JAVA, 1);
    }

    @Test
    void 풀이를_수정_할_수_있다() {
        // given
        final String title = "수정제목";
        final AlgorithmConstant algorithm = DFS;
        final DataStructureConstant dataStructure = QUEUE;
        final int level = 2;
        final String code = "수정코드";
        final String description = "수정설명";
        // when
        solution.update("수정제목", algorithm, dataStructure, level, "수정코드", "수정설명");
        // then
        Assertions.assertAll(
                () -> assertThat(solution.getTitle()).isEqualTo(title),
                () -> assertThat(solution.getAlgorithm()).isEqualTo(algorithm),
                () -> assertThat(solution.getDataStructure()).isEqualTo(dataStructure),
                () -> assertThat(solution.getLevel()).isEqualTo(level),
                () -> assertThat(solution.getCode()).isEqualTo(code),
                () -> assertThat(solution.getDescription()).isEqualTo(description)
        );
    }
}