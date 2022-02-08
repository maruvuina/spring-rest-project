package com.epam.esm.service;

import com.epam.esm.dao.OrderRepository;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.mapper.GiftCertificateMapper;
import com.epam.esm.service.mapper.OrderMapper;
import com.epam.esm.service.mapper.UserMapper;
import com.epam.esm.service.validator.OrderValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    private final OrderMapper orderMapper = Mockito.mock(OrderMapper.class);
    private final UserService userService = Mockito.mock(UserService.class);
    private final UserMapper userMapper = Mockito.mock(UserMapper.class);
    private final GiftCertificateService giftCertificateService = Mockito.mock(GiftCertificateService.class);
    private final GiftCertificateMapper giftCertificateMapper = Mockito.mock(GiftCertificateMapper.class);
    private final OrderValidator orderValidator = Mockito.mock(OrderValidator.class);
    private OrderService orderService;
    private final Long id = 1L;
    private Order order;
    private OrderDto expected;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(orderRepository, orderMapper, userService,
                userMapper, giftCertificateService, giftCertificateMapper, orderValidator);
        order = new Order();
        order.setId(id);
        expected = OrderDto.builder().id(id).build();
    }

    @Test
    void whenTagIdIsProvided_thenRetrieveTag() {
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(orderMapper.mapToDto(Mockito.any(Order.class))).thenReturn(expected);
        OrderDto actual = orderService.retrieveById(id);
        assertThat(actual.getId()).isEqualTo(expected.getId());
    }
}
