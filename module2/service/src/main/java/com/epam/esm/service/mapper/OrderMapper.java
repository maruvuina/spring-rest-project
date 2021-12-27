package com.epam.esm.service.mapper;

import com.epam.esm.dao.entity.Order;
import com.epam.esm.service.dto.OrderDto;

/**
 * The interface Order mapper.
 */
public interface OrderMapper extends MapperDto<Order, OrderDto> {

    /**
     * Map to order.
     *
     * @param orderDto             the order dto
     * @param transferredOrderData the transferred order data
     * @return the order
     */
    Order mapTo(OrderDto orderDto, TransferredOrderData transferredOrderData);
}
