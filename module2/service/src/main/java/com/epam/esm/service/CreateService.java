package com.epam.esm.service;

/**
 * This is an interface for create service operations.
 *
 * @param <T> the type of dto entity
 */
public interface CreateService<T> {

    /**
     * Create entity.
     *
     * @param t the dto entity
     * @return the dto entity
     */
    T create(T t);
}
