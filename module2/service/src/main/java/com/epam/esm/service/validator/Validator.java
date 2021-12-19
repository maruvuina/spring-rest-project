package com.epam.esm.service.validator;

import com.epam.esm.service.exception.ServiceException;

import static com.epam.esm.service.exception.ErrorCode.ERROR_001400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_002400;

/**
 * This is an interface for basic validation operations.
 *
 * @param <T> the type of dto entity
 */
public interface Validator<T> {

    /**
     * Validated id.
     *
     * @param id the id
     */
    default void validatedIdPathVariable(Long id) {
        if (id == null || id <= 0) {
            throw new ServiceException(ERROR_001400);
        }
    }

    /**
     * Validate id when create.
     *
     * @param id the id
     */
    default void validateIdWhenCreate(Long id) {
        if (id != null) {
            throw new ServiceException(ERROR_002400);
        }
    }

    /**
     * Validate name.
     *
     * @param name the name
     */
    void validateName(String name);

    /**
     * Validate.
     *
     * @param t the dto entity
     */
    void validate(T t);
}
