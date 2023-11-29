package com.prograngers.backend.dto.member.response;

import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static com.prograngers.backend.support.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.support.fixture.SolutionFixture.공개_풀이;
import static org.assertj.core.api.Assertions.assertThat;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShowMemberProfileResponseTest {
    private static final Long LAST_PAGE_CURSOR = -1L;

    @DisplayName("마지막 페이지가 아닐 경우 마지막 풀이의 id를 커서값으로 반환한다.")
    @Test
    void fromTest() {
        // given
        final Long memberId = 1L;
        final Member member = 장지담.아이디_지정_생성(memberId);
        final Problem problem = 백준_문제.기본_정보_생성();
        final Solution solution1 = 공개_풀이.아이디_지정_생성(1L, problem, member, LocalDateTime.now(), JAVA, 1);
        final Solution solution2 = 공개_풀이.아이디_지정_생성(2L, problem, member, LocalDateTime.now(), JAVA, 2);
        final Solution solution3 = 공개_풀이.아이디_지정_생성(3L, problem, member, LocalDateTime.now(), JAVA, 3);

        final List<Solution> solutions = List.of(solution1, solution2, solution3);

        // when
        ShowMemberProfileResponse result = ShowMemberProfileResponse.from(member, Collections.emptyList(), solutions,
                1L,
                2L);

        // then
        assertThat(result.getCursor()).isEqualTo(solution3.getId());
    }

    @DisplayName("마지막 페이지일 경우 커서값으로 -1L을 반환한다.")
    @Test
    void fromWhenLastTest() {
        // given
        final Long memberId = 1L;
        final Member member = 장지담.아이디_지정_생성(memberId);
        final Problem problem = 백준_문제.기본_정보_생성();
        final Solution solution1 = 공개_풀이.아이디_지정_생성(1L, problem, member, LocalDateTime.now(), JAVA, 1);
        final Solution solution2 = 공개_풀이.아이디_지정_생성(2L, problem, member, LocalDateTime.now(), JAVA, 2);

        final List<Solution> solutions = List.of(solution1, solution2);

        // when
        ShowMemberProfileResponse result = ShowMemberProfileResponse.from(member, Collections.emptyList(), solutions,
                1L,
                2L);

        // then
        assertThat(result.getCursor()).isEqualTo(LAST_PAGE_CURSOR);
    }
}