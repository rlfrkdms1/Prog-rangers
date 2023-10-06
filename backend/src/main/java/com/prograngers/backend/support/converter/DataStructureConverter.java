package com.prograngers.backend.support.converter;

import com.prograngers.backend.entity.solution.DataStructureConstant;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DataStructureConverter implements AttributeConverter<DataStructureConstant, String> {

    @Override
    public String convertToDatabaseColumn(DataStructureConstant attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public DataStructureConstant convertToEntityAttribute(String dbData) {
        return StringUtils.isEmpty(dbData) ? null : DataStructureConstant.valueOf(dbData);
    }
}
