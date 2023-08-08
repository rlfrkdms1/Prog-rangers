package com.prograngers.backend;

import lombok.Getter;

@Getter
public class AuthResult {

    private String accessToken;
    private String refreshToken;

    public AuthResult(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
