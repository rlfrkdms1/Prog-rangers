package com.prograngers.backend.service.auth;

import com.prograngers.backend.dto.request.auth.SignUpRequest;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.exception.unauthorization.AlreadyExistMemberException;
import com.prograngers.backend.exception.unauthorization.AlreadyExistNicknameException;
import com.prograngers.backend.repository.RefreshTokenRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.prograngers.backend.support.fixture.MemberFixture.길가은;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @InjectMocks
    AuthService authService;
    @Mock
    MemberRepository memberRepository;
    @Mock JwtTokenProvider jwtTokenProvider;
    @Mock RefreshTokenRepository refreshTokenRepository;

    @Test
    @DisplayName("같은 닉네임을 가진 회원이 이미 존재할 경우 중복 검사시 예외를 반환한다.")
    public void 닉네임이_중복검사시_예외를_반환할_수_있다(){
        String nickname = "rlfrkdms1";
        given(memberRepository.findByNickname(nickname)).willReturn(Optional.of(길가은.기본_정보_생성()));
        assertAll(
                () -> assertThatThrownBy(() -> authService.validNicknameDuplication(nickname)).isExactlyInstanceOf(AlreadyExistNicknameException.class),
                () -> verify(memberRepository).findByNickname(nickname)
        );
    }

    @Test
    public void 닉네임_중복검사를_할_수_있다() {
        String nickname = "rlfrkdms1";
        given(memberRepository.findByNickname(nickname)).willReturn(Optional.empty());
        authService.validNicknameDuplication(nickname);
        verify(memberRepository).findByNickname(nickname);
    }

    @Test
    public void 이미_가입된_회원의_경우_예외를_반환한다(){
        String email = "rlfrkdms1@naver.com";
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(길가은.이메일_추가_생성(email)));
        assertAll(
                () -> assertThatThrownBy(() -> authService.signUp(new SignUpRequest("1234", email, "rlfrkdms"))).isExactlyInstanceOf(AlreadyExistMemberException.class),
                () -> verify(memberRepository).findByEmail(email)
        );
    }

    @Test
    @DisplayName("회원가입시 존재하는 닉네임으로 요청하면 예외를 반환한다.")
    public void 이미_존재하는_닉네임의_경우_예외를_반환한다(){
        String nickname = "rlfrkdms1";
        given(memberRepository.findByNickname(nickname)).willReturn(Optional.of(길가은.기본_정보_생성()));
        assertAll(
                () -> assertThatThrownBy(() -> authService.signUp(new SignUpRequest("1234", "email", nickname))).isExactlyInstanceOf(AlreadyExistNicknameException.class),
                () -> verify(memberRepository).findByNickname(nickname)
        );
    }


}