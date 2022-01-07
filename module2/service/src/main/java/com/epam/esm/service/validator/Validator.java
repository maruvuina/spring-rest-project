package com.epam.esm.service.validator;

import com.epam.esm.service.exception.ServiceException;

import static com.epam.esm.service.exception.ErrorCode.ERROR_001400;

/**
 * This is an interface for basic validation operations.
 *
 * @param <T> the type of dto entity
 */
public interface Validator<T> {

    /**
     * Validate id when create.
     *
     * @param id the id
     */
    default void validateIdWhenCreate(Long id) {
        if (id != null) {
            throw new ServiceException(ERROR_001400);
        }
    }

    /**
     * Validate.
     *
     * @param t the dto entity
     */
    void validate(T t);
}
