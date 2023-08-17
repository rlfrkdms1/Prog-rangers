package com.prograngers.backend.dto.response.auth;

import com.prograngers.backend.entity.Member;
import lombok.Data;

@Data
public class GoogleUserInfoResponse {
    private String id;
    private String name;
    private String email;
    private String picture;

    public Member toMember() {
        return Member.builder()
                .socialId(Long.valueOf(id.hashCode()))
                .name(name)
                .email(email)
                .build();
    }
}
