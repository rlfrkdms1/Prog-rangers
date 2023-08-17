package com.prograngers.backend.dto.response.auth.naver;

import com.prograngers.backend.entity.Member;
import com.prograngers.backend.entity.MemberType;
import lombok.Data;

@Data
public class NaverUserInfoResponse {
    private Response response;

    public Member toMember(){
        return Member.builder()
                .name(response.getName())
                .email(response.getEmail())
                .socialId(Long.valueOf(response.getId().hashCode()))
                .type(MemberType.NAVER)
                .nickname(response.getNickname())
                .phoneNumber(response.getMobile())
                .build();
    }
}
