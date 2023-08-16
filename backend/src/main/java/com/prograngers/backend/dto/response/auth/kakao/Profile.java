package com.prograngers.backend.dto.response.auth.kakao;

import lombok.Data;

@Data
public class Profile{
    private String nickname;
    private String profile_image_url;
}