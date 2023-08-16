package com.prograngers.backend.dto.response.auth;

import lombok.Data;

@Data
public class GoogleTokenResponse {
    private String access_token;
    private String refresh_token;
    private String scope;
    private String token_type;
    private Integer expires_in;
}
