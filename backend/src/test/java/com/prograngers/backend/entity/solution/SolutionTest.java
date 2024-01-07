package com.prograngers.backend.entity.solution;

import static com.prograngers.backend.entity.solution.AlgorithmConstant.BELLMAN_FORD;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SolutionTest {

    private Solution solution;
    private Member member;
    private Problem problem;

    @BeforeEach
    void beforeEach() {
        member = 장지담.기본_정보_생성();
        problem = 백준_문제.기본_정보_생성();
        solution = 공개_풀이.알고리즘_자료구조_지정_생성(problem, member, LocalDateTime.now(), JAVA, 1, BELLMAN_FORD, QUEUE);
    }

    @Test
    void 풀이를_수정_할_수_있다() {
        // given
        Solution toUpdate = 수정용_풀이_생성("수정제목", DFS, QUEUE, 2,
                "수정코드", "수정설명");

        // when
        solution.update(toUpdate);

        // then
        Assertions.assertAll(
                () -> assertThat(solution.getTitle()).isEqualTo(toUpdate.getTitle()),
                () -> assertThat(solution.getAlgorithm()).isEqualTo(toUpdate.getAlgorithm()),
                () -> assertThat(solution.getDataStructure()).isEqualTo(toUpdate.getDataStructure()),
                () -> assertThat(solution.getLevel()).isEqualTo(toUpdate.getLevel()),
                () -> assertThat(solution.getCode()).isEqualTo(toUpdate.getCode()),
                () -> assertThat(solution.getDescription()).isEqualTo(toUpdate.getDescription())
        );
    }

    @Test
    void 수정하려는_값이_null이면_수정되지_않는다() {
        // given
        Solution toUpdate = 수정용_풀이_생성(null, null, null, 2,
                null, null);
        Solution expected = 공개_풀이.알고리즘_자료구조_지정_생성(solution.getProblem(), solution.getMember(), solution.getCreatedAt(),
                solution.getLanguage(), solution.getLevel(), solution.getAlgorithm(), solution.getDataStructure());

        // when
        solution.update(toUpdate);

        // then
        Assertions.assertAll(
                () -> assertThat(solution.getTitle()).isEqualTo(expected.getTitle()),
                () -> assertThat(solution.getAlgorithm()).isEqualTo(expected.getAlgorithm()),
                () -> assertThat(solution.getDataStructure()).isEqualTo(expected.getDataStructure()),
                () -> assertThat(solution.getLevel()).isEqualTo(toUpdate.getLevel()),
                () -> assertThat(solution.getCode()).isEqualTo(expected.getCode()),
                () -> assertThat(solution.getDescription()).isEqualTo(expected.getDescription())
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "        "})
    void 수정하려는_값이_blank이면_수정되지_않는다(String blank) {
        // given
        Solution toUpdate = 수정용_풀이_생성(blank, DFS, QUEUE, 2, blank, blank);
        Solution expected = 공개_풀이.알고리즘_자료구조_지정_생성(solution.getProblem(), solution.getMember(), solution.getCreatedAt(),
                solution.getLanguage(), solution.getLevel(), toUpdate.getAlgorithm(), toUpdate.getDataStructure());

        // when
        solution.update(toUpdate);

        // then
        Assertions.assertAll(
                () -> assertThat(solution.getTitle()).isEqualTo(expected.getTitle()),
                () -> assertThat(solution.getAlgorithm()).isEqualTo(expected.getAlgorithm()),
                () -> assertThat(solution.getDataStructure()).isEqualTo(expected.getDataStructure()),
                () -> assertThat(solution.getLevel()).isEqualTo(2),
                () -> assertThat(solution.getCode()).isEqualTo(expected.getCode()),
                () -> assertThat(solution.getDescription()).isEqualTo(expected.getDescription())
        );
    }


    private Solution 수정용_풀이_생성(String title, AlgorithmConstant algorithm, DataStructureConstant dataStructure,
                               int level, String code, String description) {
        return Solution.builder()
                .title(title)
                .algorithm(algorithm)
                .dataStructure(dataStructure)
                .level(level)
                .code(code)
                .description(description)
                .build();
    }
}