package com.prograngers.backend.entity.badge;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BadgeConstant {
    새싹(1),
    잎과줄기(10),
    꽃봉오리(50),
    꽃(100),
    화단(150);

    private int count;

}
