package com.epam.esm.service.mapper.impl;

import com.epam.esm.dao.entity.Role;
import com.epam.esm.dao.entity.Status;
import com.epam.esm.dao.entity.User;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User mapTo(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .role(Role.USER)
                .status(Status.ACTIVE)
                .build();
    }

    @Override
    public UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .role(String.valueOf(user.getRole()))
                .status(String.valueOf(user.getStatus()))
                .build();
    }
}
