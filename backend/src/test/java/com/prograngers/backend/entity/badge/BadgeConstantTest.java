package com.prograngers.backend.entity.badge;

import static java.lang.Math.random;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BadgeConstantTest {

    @DisplayName("작성한 풀이 갯수에 알맞게 뱃지를 얻을 수 있다.")
    @ParameterizedTest
    @CsvSource({"새싹", "잎과줄기", "꽃봉오리", "꽃", "화단"})
    void getBadgeTest(BadgeConstant badgeConstant) {
        assertThat(BadgeConstant.getBadge(badgeConstant.getCount())).isEqualTo(badgeConstant);
    }

    @DisplayName("뱃지를 얻는 풀이 개수에 해당되지 않으면 null을 반환한다")
    @ParameterizedTest
    @CsvSource({"새싹", "잎과줄기", "꽃봉오리", "꽃", "화단"})
    void cantGetBadgeTest(BadgeConstant badgeConstant) {
        assertThat(BadgeConstant.getBadge(badgeConstant.getCount() + (int) random() * 5 + 1)).isEqualTo(null);
    }
}