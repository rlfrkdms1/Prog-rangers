package com.prograngers.backend.entity.solution;

import static com.prograngers.backend.exception.errorcode.SolutionErrorCode.LANGUAGE_NOT_EXISTS;

import com.prograngers.backend.entity.HashTag;
import com.prograngers.backend.exception.EnumTypeException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LanguageConstant implements HashTag {
    PYTHON("Python"),
    JAVA("Java"),
    CPP("C++"),
    C("C");
    private final String view;

    public static LanguageConstant from(String value) {
        for (LanguageConstant language : LanguageConstant.values()) {
            if (language.getView().equals(value)) {
                return language;
            }
        }
        throw new EnumTypeException(LANGUAGE_NOT_EXISTS);
    }

    public String getView() {
        return view;
    }
}
