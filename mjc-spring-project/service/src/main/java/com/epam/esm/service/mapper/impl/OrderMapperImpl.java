package com.epam.esm.service.mapper.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.entity.User;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.mapper.GiftCertificateMapper;
import com.epam.esm.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapperImpl implements OrderMapper {

    private final GiftCertificateMapper giftCertificateMapper;

    @Override
    public Order mapTo(User user, GiftCertificate giftCertificate) {
        return Order.builder()
                .user(user)
                .giftCertificate(giftCertificate)
                .purchaseDate(retrieveDate())
                .cost(giftCertificate.getPrice())
                .build();
    }

    @Override
    public OrderDto mapToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .giftCertificate(giftCertificateMapper.mapToDto(order.getGiftCertificate()))
                .purchaseDate(order.getPurchaseDate())
                .cost(order.getCost())
                .build();
    }
}
