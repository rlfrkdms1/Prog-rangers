package com.prograngers.backend.support.fixture;

import static com.prograngers.backend.entity.member.Member.MemberBuilder;
import static com.prograngers.backend.entity.member.MemberType.BASIC;
import static com.prograngers.backend.entity.member.MemberType.GOOGLE;
import static com.prograngers.backend.entity.member.MemberType.NAVER;

import com.prograngers.backend.dto.auth.request.LoginRequest;
import com.prograngers.backend.dto.auth.request.SignUpRequest;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MemberFixture {

    장지담("jidam99", NAVER, "jidam@dgu.ac.kr", "testPassword"),
    길가은("rlfrkdms1", BASIC, "rlfrkdms@dgu.ac.kr", "testPassword"),
    이수빈("bingbing", GOOGLE, "bingbing@dgu.ac.kr", "testPassord");

    private final String nickname;
    private final MemberType type;
    private final String email;
    private final String password;

    public MemberBuilder 기본_정보_빌더_생성() {
        return Member.builder()
                .usable(true)
                .nickname(nickname)
                .type(type)
                .email(email)
                .password(password);
    }

    public Member 기본_정보_생성() {
        return 기본_정보_빌더_생성().build();
    }

    public Member 아이디_지정_생성(Long id) {
        return 기본_정보_빌더_생성()
                .id(id)
                .build();
    }

    public Member 이메일_추가_생성(String email) {
        return 기본_정보_빌더_생성()
                .email(email).build();
    }

    public Member 일반_회원_생성(Long id, String email, String password) {
        return 기본_정보_빌더_생성()
                .id(id)
                .email(email)
                .password(password)
                .build();
    }

    public Member 일반_회원_생성(String email, String password) {
        return 일반_회원_생성(null, email, password);
    }

    public SignUpRequest 회원_가입_요청_생성(String email, String password) {
        return new SignUpRequest(password, email, this.nickname);
    }

    public LoginRequest 로그인_요청_생성(String email, String password) {
        return new LoginRequest(email, password);
    }

    public LoginRequest 로그인_요청_생성() {
        return new LoginRequest(email, password);
    }

    public Member 탈퇴_회원_생성() {
        return 기본_정보_빌더_생성()
                .usable(false)
                .build();
    }


}
