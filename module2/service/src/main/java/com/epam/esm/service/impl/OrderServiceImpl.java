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
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.mapper.GiftCertificateMapper;
import com.epam.esm.service.mapper.OrderMapper;
import com.epam.esm.service.mapper.UserMapper;
import com.epam.esm.service.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.service.exception.ErrorCode.ERROR_301404;

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
    private final OrderValidator orderValidator;

    @Override
    @Transactional
    public OrderDto create(OrderCreateDto orderCreateDto) {
        orderValidator.validate(orderCreateDto);
        User user = userMapper.mapTo(userService.retrieveById(orderCreateDto.getUserId()));
        GiftCertificate giftCertificate = giftCertificateMapper.mapToGiftCertificateForOrder(giftCertificateService
                .retrieveById(orderCreateDto.getGiftCertificateId()));
        Order createdOrder = orderDao.create(orderMapper.mapTo(user, giftCertificate));
        return orderMapper.mapToDto(createdOrder);
    }

    @Override
    public List<OrderDto> retrieveByUserId(Long userId, Page page) {
        userService.existsById(userId);
        userService.hasUserOrders(userId);
        return orderDao.retrieveByUserId(userId, page)
                .stream()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> retrieveAll(Page page) {
        return orderDao.findAll(page)
                .stream()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto retrieveById(Long id) {
        return orderMapper.mapToDto(orderDao.findById(id)
                .orElseThrow(() -> {
                    log.error("There is no order with id = {}", id);
                    return new ServiceException(ERROR_301404, String.valueOf(id));
                }));
    }
}
