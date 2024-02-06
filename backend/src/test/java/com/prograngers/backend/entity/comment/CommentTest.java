package com.prograngers.backend.entity.comment;

import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.support.fixture.CommentFixture.생성된_댓글;
import static com.prograngers.backend.support.fixture.CommentFixture.수정된_댓글;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static com.prograngers.backend.support.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.support.fixture.SolutionFixture.공개_풀이;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CommentTest {
    private Comment comment;
    private Solution solution;
    private Member member;
    private Problem problem;

    @BeforeEach
    void beforeEach() {
        member = 장지담.기본_정보_생성();
        problem = 백준_문제.기본_정보_생성();
        solution = 공개_풀이.기본_정보_생성(problem, member, LocalDateTime.now(), JAVA, 1);
        comment = 생성된_댓글.기본_정보_생성(member, solution, LocalDateTime.now());
    }

    @DisplayName("댓글을 수정할 수 있다.")
    @Test
    void updateTest() {
        // given
        Comment updated = 수정된_댓글.기본_정보_생성(member, solution, LocalDateTime.now());

        // when
        comment.update(updated.getContent());

        // then
        assertAll(
                () -> assertThat(comment.getContent()).isEqualTo(updated.getContent()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "    "})
    void 수정하려는_내용이_blank일_경우_수정할_수_없다(String content) {
        // given
        Comment expected = 생성된_댓글.기본_정보_생성(member, solution, LocalDateTime.now());

        // when
        comment.update(content);

        // then
        assertAll(
                () -> assertThat(comment.getContent()).isEqualTo(expected.getContent()));
    }

    @Test
    void 수정하려는_내용이_null일_경우_수정할_수_없다() {
        // given
        Comment expected = 생성된_댓글.기본_정보_생성(member, solution, LocalDateTime.now());

        // when
        comment.update(null);

        // then
        assertAll(
                () -> assertThat(comment.getContent()).isEqualTo(expected.getContent())
        );
    }
}