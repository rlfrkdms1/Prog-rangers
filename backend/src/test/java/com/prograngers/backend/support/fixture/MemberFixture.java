package com.prograngers.backend.support.fixture;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import lombok.AllArgsConstructor;

import static com.prograngers.backend.entity.member.Member.*;

@AllArgsConstructor
public enum MemberFixture {

    장지담("jidam99", MemberType.NAVER),
    길가은("rlfrkdms1", MemberType.BASIC);
    private final String nickname;
    private final MemberType type;

    public MemberBuilder 기본_정보_빌더_생성(){
        return Member.builder()
                .nickname(nickname)
                .type(type);
    }
    public Member 기본_정보_생성(){
        return 기본_정보_빌더_생성().build();
    }

    public Member 아이디_지정_생성(Long id){
        return 기본_정보_빌더_생성()
                .id(id)
                .build();
    }

    public Member 이메일_추가_생성(String email) {
        return 기본_정보_빌더_생성()
                .email(email).build();
    }
}
