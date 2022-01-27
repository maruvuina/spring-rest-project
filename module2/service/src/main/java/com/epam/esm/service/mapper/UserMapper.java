package com.epam.esm.service.mapper;

import com.epam.esm.dao.entity.User;
import com.epam.esm.service.dto.UserDto;

/**
 * The interface User mapper.
 */
public interface UserMapper extends Mapper<User, UserDto>,
        MapperDto<User, UserDto> {}
