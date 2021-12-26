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
     * @param t the entity
     */
    void delete(T t);

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
     * @param page the page
     * @param size the size
     * @return the list of entities
     */
    List<T> findAll(Integer page, Integer size);
}
