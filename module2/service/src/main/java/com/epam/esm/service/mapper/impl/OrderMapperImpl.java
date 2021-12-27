package com.epam.esm.service.mapper.impl;

import com.epam.esm.dao.entity.Order;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.mapper.OrderMapper;
import com.epam.esm.service.mapper.TransferredOrderData;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order mapTo(OrderDto orderDto, TransferredOrderData transferredOrderData) {
        return Order.builder()
                .id(orderDto.getId())
                .user(transferredOrderData.getUser())
                .giftCertificate(transferredOrderData.getGiftCertificate())
                .purchaseDate(orderDto.getPurchaseDate())
                .cost(orderDto.getCost())
                .build();
    }

    @Override
    public OrderDto mapToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .giftCertificateId(order.getGiftCertificate().getId())
                .purchaseDate(order.getPurchaseDate())
                .cost(order.getCost())
                .build();
    }
}
