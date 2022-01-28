package com.epam.esm.service.impl;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.dao.util.Page;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.service.exception.ErrorCode.ERROR_401404;
import static com.epam.esm.service.exception.ErrorCode.ERROR_402404;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> retrieveAll(Page page) {
        return userRepository.findAll(PageRequest.of(page.getPageNumber(), page.getSize()))
                .stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto retrieveById(Long id) {
        return userMapper.mapToDto(userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("There is no user with id = {}", id);
                    return new ServiceException(ERROR_401404, String.valueOf(id));
                }));
    }

    @Override
    public void checkIfUserMakeOrders(Long id) {
        if (!userRepository.hasUserOrders(id)) {
            log.error("User with id = {} does not make orders", id);
            throw new ServiceException(ERROR_402404, String.valueOf(id));
        }
    }

    @Override
    public void checkIfUserExistsById(Long id) {
        if (!userRepository.existsById(id)) {
            log.error("User with id = {} does not exists", id);
            throw new ServiceException(ERROR_401404, String.valueOf(id));
        }
    }
}
