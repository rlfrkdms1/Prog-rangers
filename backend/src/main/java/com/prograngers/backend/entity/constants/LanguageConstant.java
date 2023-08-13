package com.prograngers.backend.entity.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.prograngers.backend.exception.enumtype.LevelNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LanguageConstant {
    PYTHON("python"),
    JAVA("java"),
    CPP("C++"),
    C("C"),
    private final String value;

    @JsonCreator
    public static LanguageConstant from(String value) {
        for (LanguageConstant language : LanguageConstant.values()) {
            if (language.getKrName().equals(value)) {
                return language;
            }
        }
        throw new LanguageNotFoundException();
    }

    @JsonValue
    public String getKrName() {
        return value;
    }

}
