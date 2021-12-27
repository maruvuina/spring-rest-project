package com.epam.esm.service;

/**
 * This is an interface for basic service operations.
 *
 * @param <T> the type of dto entity
 */
public interface DeleteService<T> {

    /**
     * Delete entity.
     *
     * @param id the id
     */
    void delete(Long id);
}
