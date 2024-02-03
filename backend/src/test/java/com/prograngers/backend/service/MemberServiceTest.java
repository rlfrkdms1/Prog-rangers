package com.prograngers.backend.service;

import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static com.prograngers.backend.support.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.support.fixture.SolutionFixture.공개_풀이;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prograngers.backend.dto.member.response.ShowMemberProfileResponse;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.NotFoundException;
import com.prograngers.backend.exception.UnAuthorizationException;
import com.prograngers.backend.repository.badge.BadgeRepository;
import com.prograngers.backend.repository.follow.FollowRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Slf4j
class MemberServiceTest {

    private static final Long LAST_PAGE_CURSOR = -1L;
    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BadgeRepository badgeRepository;
    @Mock
    private SolutionRepository solutionRepository;
    @Mock
    private FollowRepository followRepository;

    @DisplayName("회원 프로필을 조회할 수 있다. 마지막 페이지가 아닌 경우 다음 풀이의 id를 커서값으로 반환한다.")
    @Test
    void getMemberProfileTest() {
        // given
        final Long memberId = 1L;
        final Member member = 장지담.아이디_지정_생성(memberId);
        final Problem problem = 백준_문제.기본_정보_생성();
        final Solution solution1 = 공개_풀이.아이디_지정_생성(1L, problem, member, LocalDateTime.now(), JAVA, 1);
        final Solution solution2 = 공개_풀이.아이디_지정_생성(2L, problem, member, LocalDateTime.now(), JAVA, 2);
        final Solution solution3 = 공개_풀이.아이디_지정_생성(3L, problem, member, LocalDateTime.now(), JAVA, 3);

        List<Solution> solutions = new ArrayList<>(Arrays.asList(solution1, solution2, solution3));

        when(memberRepository.findByNickname(member.getNickname())).thenReturn(Optional.of(member));
        when(badgeRepository.findAllByMember(member)).thenReturn(Collections.emptyList());
        when(solutionRepository.findProfileSolutions(memberId, Long.MAX_VALUE)).thenReturn(solutions);
        when(followRepository.countFollow(member)).thenReturn(1L);
        when(followRepository.countFollowing(member)).thenReturn(1L);

        final ShowMemberProfileResponse expected = ShowMemberProfileResponse.from(member, Collections.emptyList(),
                new ArrayList<>(Arrays.asList(solution1, solution2)), 1L, 1L, solution3.getId());

        // when
        final ShowMemberProfileResponse actual = memberService.getMemberProfile(member.getNickname(), Long.MAX_VALUE);

        // then
        assertAll(
                () -> assertThat(actual).usingRecursiveComparison().isEqualTo(expected),
                () -> assertThat(actual.getCursor()).isEqualTo(solution3.getId()),
                () -> verify(memberRepository, times(1)).findByNickname(member.getNickname()),
                () -> verify(badgeRepository, times(1)).findAllByMember(member),
                () -> verify(solutionRepository, times(1)).findProfileSolutions(memberId, Long.MAX_VALUE),
                () -> verify(followRepository, times(1)).countFollow(member),
                () -> verify(followRepository, times(1)).countFollowing(member)
        );
    }

    @DisplayName("회원 프로필 조회 시 마지막 페이지인 경우 커서값으로 -1L을 반환한다.")
    @Test
    void getMemberProfileWhenLastPageTest() {
        // given
        final Long memberId = 1L;
        final Member member = 장지담.아이디_지정_생성(memberId);
        final Problem problem = 백준_문제.기본_정보_생성();
        final Solution solution1 = 공개_풀이.아이디_지정_생성(1L, problem, member, LocalDateTime.now(), JAVA, 1);
        final Solution solution2 = 공개_풀이.아이디_지정_생성(2L, problem, member, LocalDateTime.now(), JAVA, 2);

        final List<Solution> solutions = List.of(solution1, solution2);

        when(memberRepository.findByNickname(member.getNickname())).thenReturn(Optional.of(member));
        when(badgeRepository.findAllByMember(member)).thenReturn(Collections.emptyList());
        when(solutionRepository.findProfileSolutions(memberId, Long.MAX_VALUE)).thenReturn(solutions);
        when(followRepository.countFollow(member)).thenReturn(1L);
        when(followRepository.countFollowing(member)).thenReturn(1L);

        final ShowMemberProfileResponse expected = ShowMemberProfileResponse.from(member, Collections.emptyList(),
                solutions, 1L, 1L, LAST_PAGE_CURSOR);

        // when
        final ShowMemberProfileResponse actual = memberService.getMemberProfile(member.getNickname(), Long.MAX_VALUE);

        // then
        assertAll(
                () -> assertThat(actual).usingRecursiveComparison().isEqualTo(expected),
                () -> assertThat(actual.getCursor()).isEqualTo(LAST_PAGE_CURSOR),
                () -> verify(memberRepository, times(1)).findByNickname(member.getNickname()),
                () -> verify(badgeRepository, times(1)).findAllByMember(member),
                () -> verify(solutionRepository, times(1)).findProfileSolutions(memberId, Long.MAX_VALUE),
                () -> verify(followRepository, times(1)).countFollow(member),
                () -> verify(followRepository, times(1)).countFollowing(member)
        );
    }

    @DisplayName("존재하지 않는 닉네임으로 회원 프로필 보기를 시도하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"바보", "babo", "notexists"})
    void getMemberProfileWhenNicknameNotExistsTest(String notExistsNickname) {
        // given
        final Member member = 장지담.기본_정보_생성();
        // when, then
        assertThatThrownBy(() -> memberService.getMemberProfile(notExistsNickname, Long.MAX_VALUE))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("탈퇴한 회원 프로필 보기를 시도하면 예외가 발생한다.")
    @Test
    void getMemberProfileWhenQuitMemberTest() {
        // given
        final Member member = 장지담.탈퇴_회원_생성();
        when(memberRepository.findByNickname(member.getNickname())).thenReturn(Optional.of(member));
        // when, then
        assertThatThrownBy(() -> memberService.getMemberProfile(member.getNickname(), Long.MAX_VALUE))
                .isInstanceOf(UnAuthorizationException.class);
    }
}