package com.prograngers.backend.entity.solution;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.prograngers.backend.entity.HashTag;
import com.prograngers.backend.exception.enumtype.DataStructureNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DataStructureConstant implements HashTag {
    LIST("리스트"),
    ARRAY("배열"),
    STACK("스택"),
    QUEUE("큐"),
    MAP("맵"),
    HEAP("힙");

    private final String view;

    @JsonCreator
    public static DataStructureConstant from(String value) {
        for (DataStructureConstant dataStructure : DataStructureConstant.values()) {
            if (dataStructure.getView().equals(value)) {
                return dataStructure;
            }
        }
        throw new DataStructureNotFoundException();
    }

    public String getView() {
        return view;
    }
}
