package com.prograngers.backend.support.converter;

import com.prograngers.backend.entity.solution.AlgorithmConstant;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AlgorithmConverter implements AttributeConverter<AlgorithmConstant, String> {

    @Override
    public String convertToDatabaseColumn(AlgorithmConstant attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public AlgorithmConstant convertToEntityAttribute(String dbData) {
        return StringUtils.isEmpty(dbData) ? null : AlgorithmConstant.valueOf(dbData);
    }
}
