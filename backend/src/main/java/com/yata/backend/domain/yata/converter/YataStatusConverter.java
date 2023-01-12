package com.yata.backend.domain.yata.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.yata.backend.domain.yata.entity.YataStatus;

public class YataStatusConverter implements Converter<String, YataStatus> {

    @Override
    public YataStatus convert(String str){
        return YataStatus.valueOf(str);
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}
