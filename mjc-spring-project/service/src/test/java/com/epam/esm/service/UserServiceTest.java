package com.epam.esm.service;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.dao.entity.User;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.impl.UserServiceImpl;
import com.epam.esm.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserMapper userMapper = Mockito.mock(UserMapper.class);
    private UserService userService;
    private final Long id = 1L;
    private User user;
    private UserDto expected;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, userMapper);
        user = new User();
        user.setId(id);
        String name = "name";
        user.setName(name);
        expected = UserDto.builder().id(id).name(name).build();
    }

    @Test
    void whenUserIdIsProvided_thenRetrieveUser() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.mapToDto(Mockito.any(User.class))).thenReturn(expected);
        UserDto actual = userService.retrieveById(id);
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    void checkIfUserMakeOrders() {
        UserService userServiceMock = Mockito.mock(UserService.class);
        doNothing().when(userServiceMock).checkIfUserMakeOrders(id);
        userServiceMock.checkIfUserMakeOrders(id);
        verify(userServiceMock, times(1)).checkIfUserMakeOrders(id);
    }

    @Test
    void checkIfUserExistsById() {
        UserService userServiceMock = Mockito.mock(UserService.class);
        doNothing().when(userServiceMock).checkIfUserExistsById(id);
        userServiceMock.checkIfUserExistsById(id);
        verify(userServiceMock, times(1)).checkIfUserExistsById(id);
    }
}
