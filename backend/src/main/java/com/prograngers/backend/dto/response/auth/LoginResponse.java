package com.prograngers.backend.dto.response.auth;

import com.prograngers.backend.dto.result.AuthResult;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String accessToken;

    public static LoginResponse from(AuthResult authResult){
        return LoginResponse.builder()
                .accessToken(authResult.getAccessToken())
                .build();
    }
}
