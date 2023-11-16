package com.prograngers.backend.dto.auth.response;

import com.prograngers.backend.dto.auth.result.AuthResult;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String nickname;
    private String accessToken;

    public static LoginResponse from(AuthResult authResult) {
        return LoginResponse.builder()
                .accessToken(authResult.getAccessToken())
                .nickname(authResult.getNickname())
                .build();
    }
}
