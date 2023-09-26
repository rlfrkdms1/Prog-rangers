package com.prograngers.backend.dto.auth.response.kakao;

import lombok.Data;

@Data
public class Profile{
    private String nickname;
    private String profile_image_url;
}