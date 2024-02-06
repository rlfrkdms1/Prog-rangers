package com.prograngers.backend.entity.badge;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BadgeConstant {
    새싹(1L),
    잎과줄기(10L),
    꽃봉오리(50L),
    꽃(100L),
    화단(150L);

    private final Long count;

    public static BadgeConstant getBadge(Long count) {
        for (BadgeConstant badge : BadgeConstant.values()) {
            if (badge.getCount().equals(count)) {
                return badge;
            }
        }
        return null;
    }

    public Long getCount() {
        return count;
    }
}
