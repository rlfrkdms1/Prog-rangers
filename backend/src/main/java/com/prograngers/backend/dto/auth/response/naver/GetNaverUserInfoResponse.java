package com.prograngers.backend.dto.auth.response.naver;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import lombok.Data;

@Data
public class GetNaverUserInfoResponse {

    private NaverSocialIdResponse naverSocialIdResponse;

    public Member toMember(){
        return Member.builder()
                .socialId(Long.valueOf(naverSocialIdResponse.getId().hashCode()))
                .type(MemberType.NAVER)
                .build();
    }
}
