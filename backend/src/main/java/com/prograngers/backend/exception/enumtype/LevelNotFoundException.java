package com.prograngers.backend.exception.enumtype;

import static com.prograngers.backend.exception.ErrorCode.LEVEL_NOT_EXISTS;

public class LevelNotFoundException extends EnumTypeException {

    public LevelNotFoundException() {
        super("레벨 타입을 확인해 주세요", LEVEL_NOT_EXISTS);
    }
}
