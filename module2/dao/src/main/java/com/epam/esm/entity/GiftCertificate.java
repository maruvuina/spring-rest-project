package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.postgresql.util.PGInterval;

import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private PGInterval duration;
    private Instant createDate;
    private Instant lastUpdateDate;
}
