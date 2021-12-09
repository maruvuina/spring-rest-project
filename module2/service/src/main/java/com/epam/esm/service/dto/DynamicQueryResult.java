package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DynamicQueryResult {

    private String query;
    private String parameter;
}
