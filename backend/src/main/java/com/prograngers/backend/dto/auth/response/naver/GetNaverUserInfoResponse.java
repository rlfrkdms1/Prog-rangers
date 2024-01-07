package com.prograngers.backend.dto.auth.response.naver;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import lombok.Data;

@Data
public class GetNaverUserInfoResponse {

    private NaverSocialIdResponse response;

    public Member toMember(String nickname) {
        return Member.builder()
                .usable(true)
                .nickname(nickname)
                .socialId(Long.valueOf(response.getId().hashCode()))
                .type(MemberType.NAVER)
                .build();
    }
}
