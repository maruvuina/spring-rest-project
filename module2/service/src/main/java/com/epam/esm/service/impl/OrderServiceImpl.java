package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.util.Page;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.OrderCreateDto;
import com.epam.esm.service.dto.OrderRetrieveDto;
import com.epam.esm.service.mapper.GiftCertificateMapper;
import com.epam.esm.service.mapper.OrderMapper;
import com.epam.esm.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateMapper giftCertificateMapper;

    @Override
    public OrderRetrieveDto create(OrderCreateDto orderCreateDto) {
        User user = userMapper.mapTo(userService.retrieveById(orderCreateDto.getUserId()));
        GiftCertificate giftCertificate = giftCertificateMapper
                .mapTo(giftCertificateService.retrieveById(orderCreateDto.getGiftCertificateId()));
        Order createdOrder = orderDao.create(orderMapper.mapTo(user, giftCertificate));
        return orderMapper.mapToDto(createdOrder);
    }

    @Override
    public List<OrderRetrieveDto> retrieveByUserId(Long userId, Page page) {
        return orderDao.retrieveByUserId(userId, page)
                .stream()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderRetrieveDto> retrieveAll(Page page) {
        return orderDao.findAll(page)
                .stream()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderRetrieveDto retrieveById(Long id) {
        return orderMapper.mapToDto(orderDao.findById(id).get());
    }
}
