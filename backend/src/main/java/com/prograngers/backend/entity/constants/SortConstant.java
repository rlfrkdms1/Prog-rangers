package com.prograngers.backend.entity.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.prograngers.backend.exception.enumtype.SortTypeNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SortConstant {
    NEWEST("NEWEST"),
    SCRAPS("SCRAPS"),
    LIKES("LIKES"),
    SOLUTIONS("SOLUTIONS");
    String stringValue;

    @JsonCreator
    public static SortConstant from(String value){
        for (SortConstant constant : SortConstant.values()){
            if (constant.getStringValue().equals(constant)){
                return  constant;
            }
        }
        throw new SortTypeNotFoundException();
    }

    @JsonValue
    public String getStringValue(){
        return this.stringValue;
    }

}
