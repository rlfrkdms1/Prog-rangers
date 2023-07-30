package com.prograngers.backend.entity.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.prograngers.backend.exception.enumtype.LevelNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LevelConstant {
    ONE("레벨1"),
    TWO("레벨2"),
    THREE("레벨3"),
    FOUR("레벨4"),
    FIVE("레벨5");

    private final String krName;

    @JsonCreator
    public static LevelConstant from(String krName) {
        for (LevelConstant level : LevelConstant.values()) {
            if (level.getKrName().equals(krName)) {
                return level;
            }
        }
        throw new LevelNotFoundException();
    }

    @JsonValue
    public String getKrName() {
        return krName;
    }

}
