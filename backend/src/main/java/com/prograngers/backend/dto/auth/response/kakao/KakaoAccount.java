package com.prograngers.backend.dto.auth.response.kakao;

import lombok.Data;

@Data
public class KakaoAccount {

    private Profile profile;
    private String email;

}


