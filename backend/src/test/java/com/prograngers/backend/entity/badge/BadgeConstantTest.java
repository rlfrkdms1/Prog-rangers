package com.prograngers.backend.entity.badge;

import static com.prograngers.backend.entity.badge.BadgeConstant.꽃;
import static com.prograngers.backend.entity.badge.BadgeConstant.꽃봉오리;
import static com.prograngers.backend.entity.badge.BadgeConstant.새싹;
import static com.prograngers.backend.entity.badge.BadgeConstant.잎과줄기;
import static com.prograngers.backend.entity.badge.BadgeConstant.화단;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BadgeConstantTest {

    @DisplayName("작성한 풀이 갯수에 알맞게 뱃지를 얻을 수 있다.")
    @Test
    void getBadgeTest() {
        assertAll(
                () -> assertThat(BadgeConstant.getBadge(1L)).isEqualTo(새싹),
                () -> assertThat(BadgeConstant.getBadge(10L)).isEqualTo(잎과줄기),
                () -> assertThat(BadgeConstant.getBadge(50L)).isEqualTo(꽃봉오리),
                () -> assertThat(BadgeConstant.getBadge(100L)).isEqualTo(꽃),
                () -> assertThat(BadgeConstant.getBadge(150L)).isEqualTo(화단)
        );
    }

}