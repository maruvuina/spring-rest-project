package com.epam.esm.service.validator;

import com.epam.esm.service.dto.AuthenticationRequest;

/**
 * The interface Authorization validator.
 */
public interface AuthorizationValidator extends Validator<AuthenticationRequest>, AuthenticationValidator {}
