package com.epam.esm.dao.util;

import lombok.Data;

@Data
public class GiftCertificateParameter {

    private String tagName;
    private String name;
    private String description;
    private SortType sort;
    private OrderType order;
}
