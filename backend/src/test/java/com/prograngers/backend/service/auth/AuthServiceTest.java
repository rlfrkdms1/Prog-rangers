package com.prograngers.backend.service.auth;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.exception.unauthorization.AlreadyExistNicknameException;
import com.prograngers.backend.repository.RefreshTokenRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.prograngers.backend.support.fixture.MemberFixture.길가은;
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
    public void 닉네임_중복검사를_할_수_있다(){
        given(memberRepository.findByNickname("rlfrkdms1")).willReturn(Optional.of(길가은.기본_정보_생성()));
        assertAll(
                () -> assertThatThrownBy(() -> authService.validNicknameDuplication("rlfrkdms1")).isExactlyInstanceOf(AlreadyExistNicknameException.class),
                () -> verify(memberRepository).findByNickname("rlfrkdms1")
        );
    }

}