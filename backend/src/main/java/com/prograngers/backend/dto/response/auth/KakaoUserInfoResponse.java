package com.prograngers.backend.dto.response.auth;

import com.prograngers.backend.entity.Member;
import lombok.Data;

import static com.prograngers.backend.entity.MemberType.KAKAO;

@Data
public class KakaoUserInfoResponse {

    private Long id;
    private KakaoAccount kakao_account;

    public Member toMember(){
        return Member.builder()
                .socialId(this.id)
                .type(KAKAO)
                .name(this.kakao_account.getProfile().getNickname())
                .email(this.kakao_account.getEmail())
                .build();
    }
}
