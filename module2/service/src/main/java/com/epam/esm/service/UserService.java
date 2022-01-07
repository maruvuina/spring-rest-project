package com.epam.esm.service;

import com.epam.esm.service.dto.UserDto;

/**
 * The interface User service.
 */
public interface UserService extends GetService<UserDto> {

    /**
     * Check if user has user orders.
     *
     * @param id the user id
     * @return the boolean
     */
    boolean hasUserOrders(Long id);
}
