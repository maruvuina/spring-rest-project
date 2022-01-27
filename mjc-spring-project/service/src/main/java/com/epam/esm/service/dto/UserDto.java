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
@Relation(collectionRelation = "users")
public class UserDto extends RepresentationModel<UserDto> {

    private Long id;
    private String email;
    private String password;
    private String name;
}
