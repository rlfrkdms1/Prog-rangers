package com.prograngers.backend.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.prograngers.backend.exception.enumtype.LevelNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Levels {
    ONE("레벨1"),
    TWO("레벨2"),
    THREE("레벨3"),
    FOUR("레벨4"),
    FIVE("레벨5");

    private final String krName;

    @JsonCreator
    public static Levels from(String krName){
        for (Levels level : Levels.values()){
            if (level.getKrName().equals(krName)){
                return level;
            }
        }
        throw new LevelNotFoundException();
    }

    @JsonValue
    public String getKrName(){
        return krName;
    }

}
