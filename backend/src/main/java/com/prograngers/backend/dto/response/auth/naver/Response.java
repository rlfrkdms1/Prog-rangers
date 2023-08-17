package com.prograngers.backend.dto.response.auth.naver;

import lombok.Data;

@Data
public class Response {

    private String id;
    private String name;
    private String email;
    private String nickname;
    private String mobile;
    private String profile_image;
}
