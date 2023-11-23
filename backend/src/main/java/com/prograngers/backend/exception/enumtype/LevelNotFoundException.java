package com.prograngers.backend.exception.enumtype;

import static com.prograngers.backend.exception.ErrorCode.LEVEL_NOT_EXISTS;

public class LevelNotFoundException extends EnumTypeException {

    public LevelNotFoundException() {
        super(LEVEL_NOT_EXISTS);
    }
}
