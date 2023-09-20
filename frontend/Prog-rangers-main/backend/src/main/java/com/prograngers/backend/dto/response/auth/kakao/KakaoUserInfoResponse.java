package com.prograngers.backend.dto.response.auth.kakao;

import com.prograngers.backend.entity.member.Member;
import lombok.Data;

import static com.prograngers.backend.entity.member.MemberType.KAKAO;

@Data
public class KakaoUserInfoResponse {

    private Long id;

    public Member toMember(){
        return Member.builder()
                .socialId(id)
                .type(KAKAO)
                .build();
    }
}
