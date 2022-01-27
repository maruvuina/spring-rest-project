package com.epam.esm.service.mapper;

import java.time.Instant;

/**
 * The interface Mapper to map entity to dto entity.
 *
 * @param <T> the type of entity
 * @param <D> the type of entity Dto
 */
public interface MapperDto<T, D> {

    /**
     * Retrieve date.
     *
     * @return the instant
     */
    default Instant retrieveDate() {
        return Instant.now();
    }

    /**
     * Map to dto entity.
     *
     * @param t the entity
     * @return the dto entity
     */
    D mapToDto(T t);
}
