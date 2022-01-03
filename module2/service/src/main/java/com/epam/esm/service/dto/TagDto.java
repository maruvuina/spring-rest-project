package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto extends RepresentationModel<TagDto> {

    private Long id;
    private String name;
}
