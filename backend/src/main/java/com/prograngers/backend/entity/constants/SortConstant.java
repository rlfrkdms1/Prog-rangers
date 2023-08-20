package com.prograngers.backend.entity.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SortConstant {
    NEWEST("newest"),
    SCRAPS("scraps"),
    LIKES("likes");
    String value;

    public String getValue() {
        return this.value;
    }
}
