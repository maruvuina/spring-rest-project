package com.epam.esm.dao.util;

import lombok.Data;

import java.util.List;

@Data
public class GiftCertificateParameter {

    private List<String> tagName;
    private String name;
    private String description;
    private SortType sort;
    private OrderType order;
}
