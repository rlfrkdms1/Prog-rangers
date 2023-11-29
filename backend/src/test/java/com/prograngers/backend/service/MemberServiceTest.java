package com.prograngers.backend.service;

import com.prograngers.backend.dto.auth.request.SignUpRequest;
import com.prograngers.backend.dto.member.response.ShowBasicMemberAccountResponse;
import com.prograngers.backend.dto.member.response.ShowMemberAccountResponse;
import com.prograngers.backend.dto.member.response.ShowSocialMemberAccountResponse;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.exception.unauthorization.AlreadyExistNicknameException;
import com.prograngers.backend.repository.badge.BadgeRepository;
import com.prograngers.backend.repository.follow.FollowRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import com.prograngers.backend.support.Encrypt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.prograngers.backend.support.fixture.MemberFixture.길가은;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BadgeRepository badgeRepository;
    @Mock
    private SolutionRepository solutionRepository;
    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void 회원의_로그인_타입에_따라_계정_정보_조회시_반환되는_DTO가_다르다_BASIC() {
        Member member = 길가은.기본_정보_생성(1L);
        String encodedPassword = "encoded";

        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
        ShowMemberAccountResponse memberAccount;
        try (MockedStatic<Encrypt> encryptMockedStatic = mockStatic(Encrypt.class)) {
            encryptMockedStatic.when(() -> Encrypt.decoding(member.getPassword())).thenReturn(encodedPassword);
            memberAccount = memberService.getMemberAccount(member.getId());
        }

        assertAll(
                () -> assertThat(memberAccount).isExactlyInstanceOf(ShowBasicMemberAccountResponse.class),
                () -> verify(memberRepository).findById(member.getId())
        );
    }

    @Test
    void 회원의_로그인_타입에_따라_계정_정보_조회시_반환되는_DTO가_다르다_SOCIAL() {
        Member member = 장지담.기본_정보_생성(1L);
        String encodedPassword = "encoded";

        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
        ShowMemberAccountResponse memberAccount;
        try (MockedStatic<Encrypt> encryptMockedStatic = mockStatic(Encrypt.class)) {
            encryptMockedStatic.when(() -> Encrypt.decoding(member.getPassword())).thenReturn(encodedPassword);
            memberAccount = memberService.getMemberAccount(member.getId());
        }

        assertAll(
                () -> assertThat(memberAccount).isExactlyInstanceOf(ShowSocialMemberAccountResponse.class),
                () -> verify(memberRepository).findById(member.getId())
        );
    }

    @Test
    void 계정정보_조회시_존재하지_않는_회원인_경우_예외를_반환한다() {
        Member member = 길가은.기본_정보_생성(1L);

        given(memberRepository.findById(member.getId())).willReturn(Optional.empty());

        assertAll(
                () -> assertThatThrownBy(() -> memberService.getMemberAccount(member.getId()))
                        .isExactlyInstanceOf(MemberNotFoundException.class),
                () -> verify(memberRepository).findById(member.getId())
        );
    }

}