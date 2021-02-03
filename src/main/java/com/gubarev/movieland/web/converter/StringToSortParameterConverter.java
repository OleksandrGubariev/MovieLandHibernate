package com.gubarev.movieland.web.converter;

import com.gubarev.movieland.common.SortParameterType;
import org.springframework.core.convert.converter.Converter;

public class StringToSortParameterConverter implements Converter<String, SortParameterType> {


    @Override
    public SortParameterType convert(String source) {
        return SortParameterType.valueOf(source.toUpperCase());
    }
}
