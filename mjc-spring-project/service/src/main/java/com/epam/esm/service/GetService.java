package com.epam.esm.service;

import com.epam.esm.dao.util.Page;

import java.util.List;

/**
 * This is an interface for get service operations.
 *
 * @param <T> the type of dto entity
 */
public interface GetService<T> {

    /**
     * Retrieve all list.
     *
     * @param page the page
     * @return the list
     */
    List<T> retrieveAll(Page page);

    /**
     * Retrieve by id.
     *
     * @param id the id
     * @return the dto entity
     */
    T retrieveById(Long id);
}
