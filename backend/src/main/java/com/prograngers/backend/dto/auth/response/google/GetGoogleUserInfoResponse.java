package com.prograngers.backend.dto.auth.response.google;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import lombok.Data;

@Data
public class GetGoogleUserInfoResponse {

    private String id;

    public Member toMember() {
        return Member.builder()
                .socialId(Long.valueOf(id.hashCode()))
                .type(MemberType.GOOGLE)
                .build();
    }
}
