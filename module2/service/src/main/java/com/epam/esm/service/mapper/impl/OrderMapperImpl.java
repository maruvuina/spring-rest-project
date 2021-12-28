package com.epam.esm.service.mapper.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.entity.User;
import com.epam.esm.service.dto.OrderRetrieveDto;
import com.epam.esm.service.mapper.GiftCertificateMapper;
import com.epam.esm.service.mapper.OrderMapper;
import com.epam.esm.service.util.DateUtil;
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
                .purchaseDate(DateUtil.retrieveDate())
                .cost(giftCertificate.getPrice())
                .build();
    }

    @Override
    public OrderRetrieveDto mapToDto(Order order) {
        return OrderRetrieveDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .giftCertificateDto(giftCertificateMapper.mapToDto(order.getGiftCertificate()))
                .purchaseDate(DateUtil.retrieveFormatterDate(order.getPurchaseDate()))
                .cost(order.getCost())
                .build();
    }
}
