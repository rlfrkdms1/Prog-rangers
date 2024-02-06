package com.prograngers.backend.entity.sortconstant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.prograngers.backend.exception.enumtype.SortTypeNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SortConstant {
    NEWEST("NEWEST"),
    SCRAPS("SCRAPS"),
    LIKES("LIKES"),
    SOLUTIONS("SOLUTIONS");

    String value;

    @JsonCreator
    public static SortConstant from(String value) {
        for (SortConstant constant : SortConstant.values()) {
            if (constant.getStringValue().equals(value)) {
                return constant;
            }
        }
        throw new SortTypeNotFoundException();
    }

    @JsonValue
    public String getStringValue() {
        return this.value;
    }

    public static class Name {
        public static final String NEWEST = "NEWEST";
    }

}
