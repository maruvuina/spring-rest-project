package com.epam.esm.service.validator;

import com.epam.esm.service.dto.UserDto;

/**
 * The interface User validator.
 */
public interface UserValidator extends Validator<UserDto>, AuthenticationValidator {}
