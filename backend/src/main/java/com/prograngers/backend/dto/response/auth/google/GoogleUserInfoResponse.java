package com.prograngers.backend.dto.response.auth.google;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
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
                .type(MemberType.GOOGLE)
                .build();
    }
}
