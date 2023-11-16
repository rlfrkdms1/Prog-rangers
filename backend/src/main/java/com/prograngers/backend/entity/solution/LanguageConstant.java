package com.prograngers.backend.entity.solution;

import com.prograngers.backend.entity.HashTag;
import com.prograngers.backend.exception.enumtype.LanguageNotFoundException;
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
        throw new LanguageNotFoundException();
    }

    public String getView() {
        return view;
    }
}
