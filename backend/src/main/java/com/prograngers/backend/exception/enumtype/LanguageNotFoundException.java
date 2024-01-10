package com.prograngers.backend.exception.enumtype;


import static com.prograngers.backend.exception.errorcode.SolutionErrorCode.LANGUAGE_NOT_EXISTS;

public class LanguageNotFoundException extends EnumTypeException {

    public LanguageNotFoundException() {
        super(LANGUAGE_NOT_EXISTS);
    }
}
