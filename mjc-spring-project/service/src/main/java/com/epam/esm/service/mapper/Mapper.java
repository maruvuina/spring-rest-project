package com.epam.esm.service.mapper;

/**
 * The interface Mapper to map dto entity to entity.
 *
 * @param <T> the type of entity
 * @param <D> the type of entity Dto
 */
public interface Mapper<T, D> {

    /**
     * Map to entity.
     *
     * @param dto the dto entity
     * @return the entity
     */
    T mapTo(D dto);
}
