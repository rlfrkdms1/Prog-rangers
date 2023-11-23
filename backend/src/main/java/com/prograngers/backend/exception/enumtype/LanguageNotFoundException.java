package com.prograngers.backend.exception.enumtype;


import static com.prograngers.backend.exception.ErrorCode.LANGUAGE_NOT_EXISTS;

public class LanguageNotFoundException extends EnumTypeException {

    public LanguageNotFoundException() {
        super(LANGUAGE_NOT_EXISTS);
    }
}
