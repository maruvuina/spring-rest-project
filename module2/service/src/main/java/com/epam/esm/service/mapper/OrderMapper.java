package com.epam.esm.service.mapper;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.entity.User;
import com.epam.esm.service.dto.OrderDto;

/**
 * The interface Order mapper.
 */
public interface OrderMapper extends MapperDto<Order, OrderDto> {

    /**
     * Map to order.
     *
     * @param user            the user
     * @param giftCertificate the gift certificate
     * @return the order
     */
    Order mapTo(User user, GiftCertificate giftCertificate);
}
