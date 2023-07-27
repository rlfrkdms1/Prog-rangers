package com.prograngers.backend.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.prograngers.backend.exception.notfound.AlgorithmNotFoundException;
import com.prograngers.backend.exception.notfound.DataStructureNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DataStructures {
    LIST("리스트"),
    ARRAY("배열"),
    STACK("스택"),
    QUEUE("큐"),
    MAP("맵"),
    HEAP("힙");
    private final String krName;

    @JsonCreator
    public static DataStructures from(String krName){
        for (DataStructures dataStructure : DataStructures.values()){
            if (dataStructure.getKrName().equals(krName)){
                return dataStructure;
            }
        }
        throw new DataStructureNotFoundException();
    }
}
