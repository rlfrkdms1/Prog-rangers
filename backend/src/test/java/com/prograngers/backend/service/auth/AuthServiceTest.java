package com.prograngers.backend.service.auth;

import static com.prograngers.backend.support.fixture.MemberFixture.길가은;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import com.prograngers.backend.dto.auth.request.LoginRequest;
import com.prograngers.backend.dto.auth.request.SignUpRequest;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.exception.InvalidValueException;
import com.prograngers.backend.exception.NotFoundException;
import com.prograngers.backend.exception.UnAuthorizationException;
import com.prograngers.backend.repository.RefreshTokenRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.support.Encrypt;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    @DisplayName("같은 닉네임을 가진 회원이 이미 존재할 경우 중복 검사시 예외를 반환한다.")
    void 닉네임이_중복검사시_예외를_반환할_수_있다() {
        String nickname = "rlfrkdms1";
        given(memberRepository.findByNickname(nickname)).willReturn(Optional.of(길가은.기본_정보_생성()));
        assertAll(
                () -> assertThatThrownBy(() -> authService.validAlreadyExistNickname(nickname)).isExactlyInstanceOf(
                        UnAuthorizationException.class),
                () -> verify(memberRepository).findByNickname(nickname)
        );
    }

    @Test
    void 닉네임_중복검사를_할_수_있다() {
        String nickname = "rlfrkdms1";
        given(memberRepository.findByNickname(nickname)).willReturn(Optional.empty());
        authService.validAlreadyExistNickname(nickname);
        verify(memberRepository).findByNickname(nickname);
    }

    @Test
    void 이미_가입된_회원의_경우_예외를_반환한다() {
        String email = "rlfrkdms1@naver.com";
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(길가은.이메일_추가_생성(email)));
        assertAll(
                () -> assertThatThrownBy(
                        () -> authService.signUp(new SignUpRequest("1234", email, "rlfrkdms"))).isExactlyInstanceOf(
                        UnAuthorizationException.class),
                () -> verify(memberRepository).findByEmail(email)
        );
    }

    @Test
    @DisplayName("회원가입시 존재하는 닉네임으로 요청하면 예외를 반환한다.")
    void 이미_존재하는_닉네임의_경우_예외를_반환한다() {
        String email = "email";
        String nickname = "rlfrkdms1";
        given(memberRepository.findByEmail(email)).willReturn(Optional.empty());
        given(memberRepository.findByNickname(nickname)).willReturn(Optional.of(길가은.기본_정보_생성()));
        assertAll(
                () -> assertThatThrownBy(
                        () -> authService.signUp(new SignUpRequest("1234", email, nickname))).isExactlyInstanceOf(
                        UnAuthorizationException.class),
                () -> verify(memberRepository).findByNickname(nickname),
                () -> verify(memberRepository).findByEmail(email)
        );
    }

    @Test
    void 회원가입을_진행할_수_있다() {
        String email = "email";
        String nickname = "rlfrkdms1";
        String expectedToken = "expectedAccessToken";
        String password = "test";
        String encodedPassword = "encodedPassword";
        SignUpRequest signUpRequest = 길가은.회원_가입_요청_생성(email, password);
        Member member = 길가은.일반_회원_생성(email, encodedPassword);
        RefreshToken refreshToken = RefreshToken.builder().memberId(member.getId())
                .refreshToken(UUID.randomUUID().toString()).build();

        try (MockedStatic<Encrypt> encryptMockedStatic = mockStatic(Encrypt.class)) {
            given(memberRepository.findByEmail(email)).willReturn(Optional.empty());
            given(memberRepository.findByNickname(nickname)).willReturn(Optional.empty());
            encryptMockedStatic.when(() -> Encrypt.encoding(password)).thenReturn(encodedPassword);
            given(memberRepository.save(any())).willReturn(member);
            given(jwtTokenProvider.createAccessToken(any())).willReturn(expectedToken);
            given(refreshTokenRepository.save(any())).willReturn(refreshToken);
            authService.signUp(signUpRequest);
        }

        assertAll(
                () -> verify(memberRepository).findByNickname(nickname),
                () -> verify(memberRepository).findByEmail(email)
                /*
                () -> verify(memberRepository.save(any())),
                () -> verify(jwtTokenProvider.createAccessToken(any())),
                () -> verify(refreshTokenRepository.save(any()))
        */
        );

    }

    @Test
    void 회원가입_진행_금지_닉네임_사용시_예외() {
        String wrongNickname = "탈퇴한 사용자";
        String email = "notExist@naver.com";
        SignUpRequest wrongRequest = new SignUpRequest("password", email, wrongNickname);
        given(memberRepository.existsByEmail(email)).willReturn(false);
        given(memberRepository.existsByNickname(wrongNickname)).willReturn(false);
        assertAll(
                () -> assertThatThrownBy(() -> authService.signUp(wrongRequest))
                        .isExactlyInstanceOf(InvalidValueException.class),
                () -> verify(memberRepository).existsByEmail(email),
                () -> verify(memberRepository).existsByNickname(wrongNickname)
        );
    }

    @Test
    void 일반_로그인_이메일이_틀렸을_떄() {
        String email = "rlfrkdms@naver.com";
        String password = "rlfrkdms";
        LoginRequest loginRequest = 길가은.로그인_요청_생성(email, password);
        given(memberRepository.findByEmail(email)).willReturn(Optional.empty());
        assertAll(
                () -> assertThatThrownBy(() -> authService.login(loginRequest)).isExactlyInstanceOf(
                        NotFoundException.class),
                () -> verify(memberRepository).findByEmail(email)
        );

    }

    @Test
    void 일반_로그인_비밀번호_틀렸을_떄() {
        String email = "rlfrkdms@naver.com";
        String correctEncodedPassword = "rlfrkdms";
        String wrongPassword = "rlfrk";
        String encodedWrongPassword = "correctEncoding";
        LoginRequest loginRequest = 길가은.로그인_요청_생성(email, wrongPassword);
        Member member = 길가은.일반_회원_생성(email, correctEncodedPassword);
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));
        try (MockedStatic<Encrypt> encryptMockedStatic = mockStatic(Encrypt.class)) {
            encryptMockedStatic.when(() -> Encrypt.encoding(wrongPassword)).thenReturn(encodedWrongPassword);

            assertAll(
                    () -> assertThatThrownBy(() -> authService.login(loginRequest)).isExactlyInstanceOf(
                            UnAuthorizationException.class),
                    () -> verify(memberRepository).findByEmail(email)
            );
        }
    }

    @Test
    void 일반_로그인() {
        String email = "rlfrkdms@naver.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        String expectedAccessToken = "expectedToken";
        LoginRequest loginRequest = 길가은.로그인_요청_생성(email, password);
        Member member = 길가은.일반_회원_생성(email, encodedPassword);
        RefreshToken refreshToken = RefreshToken.builder().memberId(member.getId()).build();
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));
        given(jwtTokenProvider.createAccessToken(member.getId())).willReturn(expectedAccessToken);
        given(refreshTokenRepository.save(any())).willReturn(refreshToken);
        try (MockedStatic<Encrypt> encryptMockedStatic = mockStatic(Encrypt.class)) {
            encryptMockedStatic.when(() -> Encrypt.encoding(password)).thenReturn(encodedPassword);
            authService.login(loginRequest);
            assertAll(
                    () -> verify(memberRepository).findByEmail(email),
                    () -> verify(jwtTokenProvider).createAccessToken(member.getId()),
                    () -> verify(refreshTokenRepository).save(any())
            );

        }
    }

    @Test
    void 탈퇴한_회원은_로그인_할_수_없다() {
        Member member = 길가은.탈퇴_회원_생성();
        LoginRequest request = 길가은.로그인_요청_생성();
        given(memberRepository.findByEmail(member.getEmail())).willReturn(Optional.of(member));
        assertAll(
                () -> assertThatThrownBy(() -> authService.login(request)).isExactlyInstanceOf(
                        UnAuthorizationException.class),
                () -> verify(memberRepository).findByEmail(member.getEmail())
        );
    }
}