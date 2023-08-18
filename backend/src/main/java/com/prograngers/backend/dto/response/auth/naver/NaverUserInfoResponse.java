package com.prograngers.backend.dto.response.auth.naver;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import lombok.Data;

@Data
public class NaverUserInfoResponse {
    private Response response;

    public Member toMember(){
        return Member.builder()
                .socialId(Long.valueOf(response.getId().hashCode()))
                .type(MemberType.NAVER)
                .nickname(response.getNickname())
                .build();
    }
}
