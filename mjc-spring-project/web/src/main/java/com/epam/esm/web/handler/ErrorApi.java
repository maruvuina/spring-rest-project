package com.epam.esm.web.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorApi {

    private String errorMessage;
    private String errorCode;
}
