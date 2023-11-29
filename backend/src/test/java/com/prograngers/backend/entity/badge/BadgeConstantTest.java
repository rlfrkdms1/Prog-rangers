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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @DisplayName("뱃지를 얻는 풀이 개수에 해당되지 않으면 null을 반환한다")
    @ParameterizedTest
    @ValueSource(longs = {0L, 9L, 49L, 99L, 149L})
    void cantGetBadgeTest(Long solutionCount) {
        assertThat(BadgeConstant.getBadge(solutionCount)).isEqualTo(null);
    }
}