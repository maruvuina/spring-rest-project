package com.epam.esm.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a tag.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    private Long id;
    private String name;
}
