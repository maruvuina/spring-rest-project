package com.epam.esm.service.mapper;

/**
 * The interface Mapper to map entity to dto entity.
 *
 * @param <T> the type of entity
 * @param <D> the type of entity Dto
 */
public interface MapperDto<T, D> {

    /**
     * Map to dto entity.
     *
     * @param t the entity
     * @return the dto entity
     */
    D mapToDto(T t);
}
