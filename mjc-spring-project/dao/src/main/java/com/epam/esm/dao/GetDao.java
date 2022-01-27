package com.epam.esm.dao;

import com.epam.esm.dao.util.Page;

import java.util.List;
import java.util.Optional;

/**
 *This is an interface for get operations of entity.
 *
 * @param <T> a concrete type of entity
 */
public interface GetDao<T> {

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
     * @return the list of entities
     */
    List<T> findAll(Page page);
}
