package com.epam.esm.dao;

/**
 *This is an interface for create operations of entity.
 *
 * @param <T> a concrete type of entity
 */
public interface CreateDao<T> {

    /**
     * Create entity.
     *
     * @param t the entity
     * @return the entity
     */
    T create(T t);
}
