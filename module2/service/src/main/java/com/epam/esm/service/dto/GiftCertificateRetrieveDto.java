package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateRetrieveDto {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
    private List<TagDto> tags = new ArrayList<>();
}
