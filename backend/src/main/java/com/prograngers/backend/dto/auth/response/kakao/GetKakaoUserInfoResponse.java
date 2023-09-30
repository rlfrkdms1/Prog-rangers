package com.prograngers.backend.dto.auth.response.kakao;

import com.prograngers.backend.entity.member.Member;
import lombok.Data;

import static com.prograngers.backend.entity.member.MemberType.KAKAO;

@Data
public class GetKakaoUserInfoResponse {

    private Long id;

    public Member toMember(){
        return Member.builder()
                .socialId(id)
                .type(KAKAO)
                .build();
    }
}
