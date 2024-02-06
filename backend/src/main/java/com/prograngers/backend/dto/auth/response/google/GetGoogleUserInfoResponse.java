package com.prograngers.backend.dto.auth.response.google;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import lombok.Data;

@Data
public class GetGoogleUserInfoResponse {

    private String id;

    public Member toMember(String nickname) {
        return Member.builder()
                .usable(true)
                .nickname(nickname)
                .socialId(Long.valueOf(id.hashCode()))
                .type(MemberType.GOOGLE)
                .build();
    }
}
