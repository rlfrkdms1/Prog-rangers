package com.prograngers.backend.dto.response.auth.kakao;

import com.prograngers.backend.entity.member.Member;
import lombok.Data;

import static com.prograngers.backend.entity.member.MemberType.KAKAO;

@Data
public class KakaoUserInfoResponse {

    private Long id;
    private KakaoAccount kakao_account;

    public Member toMember(){
        return Member.builder()
                .socialId(this.id)
                .type(KAKAO)
                .build();
    }
}
