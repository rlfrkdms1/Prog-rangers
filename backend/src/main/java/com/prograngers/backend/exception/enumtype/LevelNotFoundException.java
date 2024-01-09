package com.prograngers.backend.exception.enumtype;

import static com.prograngers.backend.exception.ErrorCodeBefore.LEVEL_NOT_EXISTS;

public class LevelNotFoundException extends EnumTypeException {

    public LevelNotFoundException() {
        super(LEVEL_NOT_EXISTS, "레벨 타입을 확인해 주세요");
    }
}
