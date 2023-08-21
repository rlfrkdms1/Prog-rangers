package com.prograngers.backend.entity.constants;

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
    public static DataStructureConstant from(String krName) {
        for (DataStructureConstant dataStructure : DataStructureConstant.values()) {
            if (dataStructure.getStringValue().equals(krName)) {
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
