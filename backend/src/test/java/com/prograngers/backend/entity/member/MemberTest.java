package com.prograngers.backend.entity.member;

import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    private static final String QUIT_NICKNAME = "탈퇴한 사용자";

    @DisplayName("탈퇴하지 않은 회원일 경우 닉네임을 정상 반환한다.")
    @Test
    void getNicknameTest() {
        Member member = 장지담.기본_정보_생성();
        assertThat(member.getNickname()).isEqualTo("jidam99");
    }

    @DisplayName("탈퇴한 회원일 경우 탈퇴한 사용자를 닉네임 대신 반환한다")
    @Test
    void getNicknameWhenQuitTest() {
        Member quit = 장지담.탈퇴_회원_생성();
        assertThat(quit.getNickname()).isEqualTo(QUIT_NICKNAME);
    }

}