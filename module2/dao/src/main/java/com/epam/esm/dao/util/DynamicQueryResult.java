package com.epam.esm.dao.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class DynamicQueryResult {

    private String query;
    private List<String> parameter;
}
