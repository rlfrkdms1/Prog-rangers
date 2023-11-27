package com.prograngers.backend.entity.member;


import static com.prograngers.backend.support.fixture.MemberFixture.장지담;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {
    private static final String QUIT_MEMBER = "탈퇴한 사용자";

    @DisplayName("탈퇴한 회원은 탈퇴한 사용자 라는 이름을 반환한다.")
    @Test
    void getNicknameWhenQuitTest() {
        Member quit = 장지담.기본_정보_생성();
        quit.delete();
        Assertions.assertThat(quit.getNickname()).isEqualTo(QUIT_MEMBER);
    }

    @DisplayName("회원을 탈퇴시킬 수 있다.")
    @Test
    void quitTest() {
        Member quit = 장지담.기본_정보_생성();
        quit.delete();
        Assertions.assertThat(quit.isUsable()).isEqualTo(false);
    }
}