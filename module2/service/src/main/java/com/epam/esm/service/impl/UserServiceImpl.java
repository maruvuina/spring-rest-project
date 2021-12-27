package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.util.Page;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;

    @Override
    public UserDto create(UserDto userDto) {
        User createdUser = userDao.create(userMapper.mapTo(userDto));
        return userMapper.mapToDto(createdUser);
    }

    @Override
    public List<UserDto> retrieveAll(Page page) {
        return userDao.findAll(page)
                .stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto retrieveById(Long id) {
        return userMapper.mapToDto(userDao.findById(id).get());
    }
}
