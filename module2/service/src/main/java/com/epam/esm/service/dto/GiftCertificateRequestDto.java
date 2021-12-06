package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateRequestDto {

    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private List<TagDto> tags = new ArrayList<>();
}
