package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

/**
 * This is an interface for basic CRUD operations.
 *
 * @param <T> a concrete type of entity
 */
public interface AbstractDao<T> {

    /**
     * Create entity.
     *
     * @param t the entity
     * @return the optional of entity
     */
    Optional<T> create(T t);

    /**
     * Delete entity.
     *
     * @param id an id of entity
     */
    void delete(Long id);

    /**
     * Find entity by id.
     *
     * @param id the id of entity
     * @return the optional of entity
     */
    Optional<T> findById(Long id);

    /**
     * Find all entities.
     *
     * @return the list of entities
     */
    List<T> findAll();
}
