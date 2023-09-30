package com.prograngers.backend.dto.auth.response.google;

import lombok.Data;

@Data
public class GetGoogleTokenResponse {
    private String access_token;
    private String refresh_token;
    private String scope;
    private String token_type;
    private Integer expires_in;
}
