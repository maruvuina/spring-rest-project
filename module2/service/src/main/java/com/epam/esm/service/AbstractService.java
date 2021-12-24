package com.epam.esm.service;

import java.util.List;

/**
 * This is an interface for basic service operations.
 *
 * @param <T> the type of dto entity
 */
public interface AbstractService<T> {

    /**
     * Create entity.
     *
     * @param t the dto entity
     * @return the dto entity
     */
    T create(T t);

    /**
     * Delete entity.
     *
     * @param id the id
     */
    void delete(Long id);

    /**
     * Retrieve all.
     *
     * @return the list
     */
    List<T> retrieveAll();

    /**
     * Retrieve by id.
     *
     * @param id the id
     * @return the dto entity
     */
    T retrieveById(Long id);
}
