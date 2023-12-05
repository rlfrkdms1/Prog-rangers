package com.prograngers.backend.controller.auth;

import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.stereotype.Component;

@Component
public class RefreshCookieProvider {

    public static final String REFRESH_TOKEN = "refreshToken";

    public ResponseCookie createCookieWithRefreshToken(String refreshToken, Long expiredAt) {
        return createBaseCookie(refreshToken)
                .maxAge(expiredAt)
                .build();
    }

    public ResponseCookieBuilder createBaseCookie(String cookieValue) {
        return ResponseCookie.from(REFRESH_TOKEN, cookieValue)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite(SameSite.NONE.attributeValue());
    }

    public ResponseCookie createLogoutCookie() {
        return createBaseCookie("")
                .maxAge(0)
                .build();
    }
}
