package com.epam.esm.dao;

/**
 *This is an interface for delete operations of entity.
 *
 * @param <T> a concrete type of entity
 */
public interface DeleteDao<T> {

    /**
     * Delete entity.
     *
     * @param t the entity
     */
    void delete(T t);
}
