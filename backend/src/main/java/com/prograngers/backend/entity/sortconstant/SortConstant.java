package com.prograngers.backend.entity.sortconstant;

import static com.prograngers.backend.exception.errorcode.CommonErrorCode.SORT_TYPE_NOT_EXISTS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.prograngers.backend.exception.EnumTypeException;
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
        throw new EnumTypeException(SORT_TYPE_NOT_EXISTS);
    }

    @JsonValue
    public String getStringValue() {
        return this.value;
    }

    public static class Name {
        public static final String NEWEST = "NEWEST";
    }

}
