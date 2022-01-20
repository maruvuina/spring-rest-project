package com.epam.esm.dao;

import com.epam.esm.dao.entity.User;

/**
 *This is an interface for dao operations of User entity.
 */
public interface UserDao extends CreateDao<User>, GetDao<User> {

    /**
     * Check if user has orders.
     *
     * @param id the user id
     * @return the boolean
     */
    boolean hasUserOrders(Long id);

    /**
     * Exists by id.
     *
     * @param id the id
     * @return the boolean
     */
    boolean existsById(Long id);
}
