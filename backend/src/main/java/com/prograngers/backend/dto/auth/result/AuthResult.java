package com.prograngers.backend.dto.auth.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResult {

    private String accessToken;
    private String refreshToken;
    private Long refreshTokenExpiredAt;


}
