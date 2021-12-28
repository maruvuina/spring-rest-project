package com.epam.esm.dao.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class DynamicQueryResult {

    private String query;
    private Map<String, Object> parameters;
}
