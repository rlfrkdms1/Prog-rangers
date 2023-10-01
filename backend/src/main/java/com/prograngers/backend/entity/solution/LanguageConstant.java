package com.prograngers.backend.entity.solution;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.prograngers.backend.entity.HashTag;
import com.prograngers.backend.exception.enumtype.LanguageNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LanguageConstant implements HashTag {
    PYTHON("PYTHON"),
    JAVA("JAVA"),
    CPP("CPP"),
    C("C");
    private final String stringValue;

    @JsonCreator
    public static LanguageConstant from(String value) {
        for (LanguageConstant language : LanguageConstant.values()) {
            if (language.getStringValue().equals(value)) {
                return language;
            }
        }
        throw new LanguageNotFoundException();
    }

    @JsonValue
    public String getStringValue() {
        return stringValue;
    }
}
