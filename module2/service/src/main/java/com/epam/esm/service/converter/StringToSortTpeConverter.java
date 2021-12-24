package com.epam.esm.service.converter;

import com.epam.esm.dao.util.SortType;
import org.springframework.core.convert.converter.Converter;

public class StringToSortTpeConverter implements Converter<String, SortType> {

    @Override
    public SortType convert(String source) {
        return SortType.valueOf(source.toUpperCase());
    }
}
