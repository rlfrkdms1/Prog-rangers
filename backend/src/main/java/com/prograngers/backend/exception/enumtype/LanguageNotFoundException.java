package com.prograngers.backend.exception.enumtype;


import static com.prograngers.backend.exception.ErrorCode.LANGUAGE_NOT_EXISTS;

public class LanguageNotFoundException extends EnumTypeException {

    public LanguageNotFoundException() {
        super("언어 타입을 확인해 주세요", LANGUAGE_NOT_EXISTS);
    }
}
