package com.epam.esm.service;

import com.epam.esm.dao.util.Page;
import com.epam.esm.service.dto.OrderCreateDto;
import com.epam.esm.service.dto.OrderRetrieveDto;

import java.util.List;

/**
 * The interface Order service.
 */
public interface OrderService extends GetService<OrderRetrieveDto> {

    /**
     * Create order.
     *
     * @param createDto the create dto
     * @return the order retrieve dto
     */
    OrderRetrieveDto create(OrderCreateDto createDto);


    /**
     * Retrieve order by user id.
     *
     * @param userId the user id
     * @param page   the page
     * @return the list of orders
     */
    List<OrderRetrieveDto> retrieveByUserId(Long userId, Page page);
}
