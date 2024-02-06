package com.prograngers.backend.entity.solution;

import static com.prograngers.backend.exception.errorcode.SolutionErrorCode.DATA_STRUCTURE_NOT_EXISTS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.prograngers.backend.entity.HashTag;
import com.prograngers.backend.exception.EnumTypeException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DataStructureConstant implements HashTag {
    LIST,
    ARRAY,
    STACK,
    QUEUE,
    MAP,
    HEAP;

    @JsonCreator
    public static DataStructureConstant from(String value) {
        for (DataStructureConstant dataStructure : DataStructureConstant.values()) {
            if (dataStructure.name().equals(value)) {
                return dataStructure;
            }
        }
        throw new EnumTypeException(DATA_STRUCTURE_NOT_EXISTS);
    }
}
