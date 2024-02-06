package com.prograngers.backend.service.auth;


import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prograngers.backend.exception.UnAuthorizationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

    private final String key = "testtoken1111111111111111test1111111111111111test1111111111111111";

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(key, 360000);

    private final FakeJwtTokenProvider fakeJwtTokenProvider = new FakeJwtTokenProvider(key, 360000);

    @Test
    @DisplayName("issuer를 넣지 않으면 MissingIssuerTokenException을 반환한다. ")
    public void 토큰_생성자가_없으면_예외를_반환한다() {
        String accessTokenWithoutIssuer = fakeJwtTokenProvider.createAccessTokenWithoutIssuer(1L);
        assertThatThrownBy(() -> jwtTokenProvider.validToken(accessTokenWithoutIssuer))
                .isExactlyInstanceOf(UnAuthorizationException.class);
    }

    @Test
    @DisplayName("issuer가 잘못되면 IncorrectIssuerTokenException을 반환한다.")
    public void 토큰_생성자가_틀리면_예외를_반환한다() {
        String accessTokenWithWrongIssuer = fakeJwtTokenProvider.createAccessTokenWithIssuer(1L, "fake");
        assertThatThrownBy(() -> jwtTokenProvider.validToken(accessTokenWithWrongIssuer))
                .isExactlyInstanceOf(UnAuthorizationException.class);
    }

    @Test
    @DisplayName("토큰 기간이 만료되면 ExpiredTokenException을 반환한다.")
    public void 토큰_기간이_만료되면_예외를_반환한다() {
        String accessToken = new FakeJwtTokenProvider(key, 0).createAccessToken(1L);
        assertThatThrownBy(() -> jwtTokenProvider.validToken(accessToken))
                .isExactlyInstanceOf(UnAuthorizationException.class);
    }

    @Test
    @DisplayName("토큰에 들어가는 claim 중 memberId의 값아 LongType이 아닐시 InvalidClaimTypeException를 반환한다. ")
    public void 토큰에_들어가는_memberId는_long이어야한다() {
        String accessToken = fakeJwtTokenProvider.createAccessToken("memberId");
        assertThatThrownBy(() -> jwtTokenProvider.getMemberId(accessToken))
                .isExactlyInstanceOf(UnAuthorizationException.class);
    }

}