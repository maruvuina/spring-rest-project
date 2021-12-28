package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRetrieveDto {

    private Long id;
    private Long userId;
    private GiftCertificateDto giftCertificateDto;
    private String purchaseDate;
    private BigDecimal cost;
}
