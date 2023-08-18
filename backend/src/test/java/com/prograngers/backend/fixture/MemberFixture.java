package com.prograngers.backend.fixture;

import com.prograngers.backend.entity.member.Member;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MemberFixture {
    길가은1(1L, "길가은1", "rlfrkdms1", "rlfrkdms1@naver.com", "password1"),
    길가은2(2L, "길가은2", "rlfrkdms2", "rlfrkdms2@naver.com", "password2"),
    길가은3(3L, "길가은3", "rlfrkdms3", "rlfrkdms3@naver.com", "password3");

    private final Long id;
    private final String name;
    private final String nickname;
    private final String email;

    private final String password;

    public Member getMember() {
        return Member.builder()
                .id(this.id)
                .nickname(this.nickname)
                .email(this.email)
                .password(this.password)
                .build();
    }

    public Member 아이디_값_지정_멤버_생성(Long id){
        return Member.builder()
                .id(id)
                .nickname(this.nickname)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
