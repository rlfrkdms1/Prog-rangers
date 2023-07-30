package com.prograngers.backend.entity.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.prograngers.backend.exception.enumtype.DataStructureNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DataStructureConstant {
    LIST("리스트"),
    ARRAY("배열"),
    STACK("스택"),
    QUEUE("큐"),
    MAP("맵"),
    HEAP("힙");
    private final String krName;

    @JsonCreator
    public static DataStructureConstant from(String krName){
        for (DataStructureConstant dataStructure : DataStructureConstant.values()){
            if (dataStructure.getKrName().equals(krName)){
                return dataStructure;
            }
        }
        throw new DataStructureNotFoundException();
    }

    @JsonValue
    public String getKrName(){
        return krName;
    }
}
