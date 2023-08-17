package com.prograngers.backend.dto.response.auth;

import lombok.Data;

@Data
public class KakaoAccount {

    private Profile profile;
    private String email;

}


