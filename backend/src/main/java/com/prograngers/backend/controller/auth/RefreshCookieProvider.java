package com.prograngers.backend.controller.auth;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class RefreshCookieProvider {

    public static final String REFRESH_TOKEN = "refreshToken";

    public ResponseCookie createCookieWithRefreshToken(String refreshToken, Long expiredAt) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .maxAge(expiredAt)
                .secure(true)
                .path("/")
                .build();
        return cookie;
    }
}
