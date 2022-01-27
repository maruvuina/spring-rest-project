package com.epam.esm.service.validator.impl;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.TagValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.epam.esm.service.exception.ErrorCode.ERROR_201400;

@Slf4j
@Component
public class TagValidatorImpl implements TagValidator {

    private static final Integer STRING_MIN_LENGTH = 1;
    private static final Integer STRING_MAX_LENGTH = 255;

    @Override
    public void validate(TagDto tagDto) {
        validateIdWhenCreate(tagDto.getId());
        validateName(tagDto.getName());
    }

    @Override
    public void validateName(String name) {
        if (name == null || name.isBlank() ||
                !(name.length() >= STRING_MIN_LENGTH && name.length() <= STRING_MAX_LENGTH)) {
            log.error("Invalid tag name = {}", name);
            throw new ServiceException(ERROR_201400);
        }
    }
}
