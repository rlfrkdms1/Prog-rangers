package com.prograngers.backend.service.auth;


import com.prograngers.backend.exception.unauthorization.IncorrectIssuerTokenException;
import com.prograngers.backend.exception.unauthorization.MissingIssuerTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtTokenProviderTest {

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
            "testtoken1111111111111111test1111111111111111test1111111111111111",
            360000
    );

    private final FakeJwtTokenProvider fakeJwtTokenProvider = new FakeJwtTokenProvider(
            "testtoken1111111111111111test1111111111111111test1111111111111111",
            360000
    );

    @Test
    @DisplayName("issuer를 넣지 않으면 MissingIssuerTokenException을 반환한다. ")
    public void 토큰_생성자가_없으면_예외를_반환한다(){
        String accessTokenWithoutIssuer = fakeJwtTokenProvider.createAccessTokenWithoutIssuer(1L);
        assertThatThrownBy(() -> jwtTokenProvider.validToken(accessTokenWithoutIssuer))
                .isExactlyInstanceOf(MissingIssuerTokenException.class);
    }

    @Test
    @DisplayName("issuer가 잘못되면 IncorrectIssuerTokenException을 반환한다.")
    public void 토큰_생성자가_틀리면_예외를_반환한다(){
        String accessTokenWithWrongIssuer = fakeJwtTokenProvider.createAccessTokenWithIssuer(1L, "fake");
        assertThatThrownBy(() -> jwtTokenProvider.validToken(accessTokenWithWrongIssuer))
                .isExactlyInstanceOf(IncorrectIssuerTokenException.class);
    }

}