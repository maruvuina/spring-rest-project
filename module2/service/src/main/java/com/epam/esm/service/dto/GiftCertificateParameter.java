package com.epam.esm.service.dto;

import lombok.Data;

@Data
public class GiftCertificateParameter {

    private String tagName;
    private String giftCertificateName;
    private String description;
    private String sort;
    private String order;
}
