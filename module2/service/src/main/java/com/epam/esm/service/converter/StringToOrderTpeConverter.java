package com.epam.esm.service.converter;

import com.epam.esm.dao.util.OrderType;
import org.springframework.core.convert.converter.Converter;

public class StringToOrderTpeConverter implements Converter<String, OrderType> {

    @Override
    public OrderType convert(String source) {
        return OrderType.valueOf(source.toUpperCase());
    }
}
