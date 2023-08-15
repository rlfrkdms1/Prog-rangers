package com.prograngers.backend.dto.response.auth;

import lombok.Data;

@Data
public class KakaoTokenResponse {

    private String token_type;

    private String access_token;

    private Integer expires_in;

    private String refresh_token;

    private Integer refresh_token_expires_in;

}
