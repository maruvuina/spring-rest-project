package com.epam.esm.service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationResponse {

    private String email;
    private String token;
}
