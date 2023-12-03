package com.prograngers.backend.entity.solution;

import static com.prograngers.backend.entity.solution.AlgorithmConstant.BELLMAN_FORD;
import static com.prograngers.backend.entity.solution.AlgorithmConstant.DFS;
import static com.prograngers.backend.entity.solution.DataStructureConstant.HEAP;
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

    private static final String TITLE = "풀이제목";
    private static final String CODE = "import\nmain\nhello world\nreturn";
    private static final String DESCRIPTION = "풀이설명";

    final static String UPDATED_TITLE = "수정제목";
    final static AlgorithmConstant UPDATED_ALGORITHM = DFS;
    final static DataStructureConstant UPDATED_DATASTRUCTURE = QUEUE;
    final static int UPDATED_LEVEL = 2;
    final static String UPDATED_CODE = "수정코드";
    final static String UPDATED_DESCRIPTION = "수정설명";

    private Solution solution;
    private Member member;
    private Problem problem;

    @BeforeEach
    void beforeEach() {
        member = 장지담.기본_정보_생성();
        problem = 백준_문제.기본_정보_생성();
        solution = 공개_풀이.알고리즘_자료구조_지정_생성(problem, member, LocalDateTime.now(), JAVA, 1, BELLMAN_FORD, HEAP);
    }

    @Test
    void 풀이를_수정_할_수_있다() {
        // when
        solution.update(UPDATED_TITLE, UPDATED_ALGORITHM, UPDATED_DATASTRUCTURE, UPDATED_LEVEL, UPDATED_CODE, "수정설명");
        // then
        Assertions.assertAll(
                () -> assertThat(solution.getTitle()).isEqualTo(UPDATED_TITLE),
                () -> assertThat(solution.getAlgorithm()).isEqualTo(UPDATED_ALGORITHM),
                () -> assertThat(solution.getDataStructure()).isEqualTo(UPDATED_DATASTRUCTURE),
                () -> assertThat(solution.getLevel()).isEqualTo(UPDATED_LEVEL),
                () -> assertThat(solution.getCode()).isEqualTo(UPDATED_CODE),
                () -> assertThat(solution.getDescription()).isEqualTo(UPDATED_DESCRIPTION)
        );
    }

    @Test
    void 수정하려는_제목이_null이면_제목이_수정되지_않는다() {
        // when
        solution.update(null, UPDATED_ALGORITHM, UPDATED_DATASTRUCTURE, UPDATED_LEVEL, UPDATED_CODE, "수정설명");
        // then
        Assertions.assertAll(
                () -> assertThat(solution.getTitle()).isEqualTo(TITLE),
                () -> assertThat(solution.getAlgorithm()).isEqualTo(UPDATED_ALGORITHM),
                () -> assertThat(solution.getDataStructure()).isEqualTo(UPDATED_DATASTRUCTURE),
                () -> assertThat(solution.getLevel()).isEqualTo(UPDATED_LEVEL),
                () -> assertThat(solution.getCode()).isEqualTo(UPDATED_CODE),
                () -> assertThat(solution.getDescription()).isEqualTo(UPDATED_DESCRIPTION)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "        "})
    void 수정하려는_제목이_blank이면_제목이_수정되지_않는다(String title) {
        // when
        solution.update(title, UPDATED_ALGORITHM, UPDATED_DATASTRUCTURE, UPDATED_LEVEL, UPDATED_CODE, "수정설명");
        // then
        Assertions.assertAll(
                () -> assertThat(solution.getTitle()).isEqualTo(TITLE),
                () -> assertThat(solution.getAlgorithm()).isEqualTo(UPDATED_ALGORITHM),
                () -> assertThat(solution.getDataStructure()).isEqualTo(UPDATED_DATASTRUCTURE),
                () -> assertThat(solution.getLevel()).isEqualTo(UPDATED_LEVEL),
                () -> assertThat(solution.getCode()).isEqualTo(UPDATED_CODE),
                () -> assertThat(solution.getDescription()).isEqualTo(UPDATED_DESCRIPTION)
        );
    }
}