package com.prograngers.backend.entity.solution;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.prograngers.backend.exception.enumtype.DataStructureNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DataStructureConstant {
    LIST("LIST"),
    ARRAY("ARRAY"),
    STACK("STACK"),
    QUEUE("QUEUE"),
    MAP("MAP"),
    HEAP("HEAP");
    private final String stringValue;

    @JsonCreator
    public static DataStructureConstant from(String value) {
        for (DataStructureConstant dataStructure : DataStructureConstant.values()) {
            if (dataStructure.getStringValue().equals(value)) {
                return dataStructure;
            }
        }
        throw new DataStructureNotFoundException();
    }

    @JsonValue
    public String getStringValue() {
        return stringValue;
    }
}
