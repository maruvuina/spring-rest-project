package com.epam.esm.dao;

import com.epam.esm.dao.entity.User;

/**
 *This is an interface for dao operations of User entity.
 */
public interface UserDao extends CreateDao<User>, GetDao<User> {}
