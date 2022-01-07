package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.util.Page;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.service.exception.ErrorCode.ERROR_401404;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> retrieveAll(Page page) {
        return userDao.findAll(page)
                .stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto retrieveById(Long id) {
        return userMapper.mapToDto(userDao.findById(id)
                .orElseThrow(() -> {
                    log.error("There is no user with id = {}", id);
                    return new ServiceException(ERROR_401404, String.valueOf(id));
                }));
    }

    @Override
    public boolean hasUserOrders(Long id) {
        return userDao.hasUserOrders(id);
    }
}
