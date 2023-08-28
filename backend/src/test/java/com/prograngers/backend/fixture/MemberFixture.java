package com.prograngers.backend.fixture;

import com.prograngers.backend.entity.member.Member;
import lombok.AllArgsConstructor;

import static com.prograngers.backend.entity.member.Member.*;

@AllArgsConstructor
public enum MemberFixture {

    장지담("jidam99");
    private final String nickname;

    public MemberBuilder 기본_정보_빌더_생성(){
        return Member.builder()
                .nickname(nickname);
    }
    public Member 기본_정보_생성(){
        return 기본_정보_빌더_생성().build();
    }

    public Member 아이디_지정_생성(Long id){
        return 기본_정보_빌더_생성()
                .id(id)
                .build();
    }
}
