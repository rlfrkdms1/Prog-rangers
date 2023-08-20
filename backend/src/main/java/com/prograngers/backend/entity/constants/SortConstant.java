package com.prograngers.backend.entity.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.prograngers.backend.exception.enumtype.SortTypeNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SortConstant {
    NEWEST("newest"),
    SCRAPS("scraps"),
    LIKES("likes"),
    SOLUTIONS("solutions");
    String value;

    @JsonCreator
    public static SortConstant from(String value){
        for (SortConstant constant : SortConstant.values()){
            if (constant.getValue().equals(constant)){
                return  constant;
            }
        }
        throw new SortTypeNotFoundException();
    }

    @JsonValue
    public String getValue(){
        return this.value;
    }

}
