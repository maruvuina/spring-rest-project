package com.epam.esm.dao.util;

import lombok.Data;

@Data
public class GiftCertificateParameter {

    private String tagName;
    private String giftCertificateName;
    private String giftCertificateDescription;
    private String sortType;
    private String orderType;
}
