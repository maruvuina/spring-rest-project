package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "tags")
public class TagDto extends RepresentationModel<TagDto> {

    private Long id;
    private String name;
}
