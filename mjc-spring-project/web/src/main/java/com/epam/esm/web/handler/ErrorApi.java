package com.epam.esm.web.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorApi {

    private String errorMessage;
    private String errorCode;
}
