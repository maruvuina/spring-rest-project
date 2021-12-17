package com.epam.esm.service.validator.impl;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.validator.TagValidator;
import org.springframework.stereotype.Component;

@Component
public class TagValidatorImpl implements TagValidator {

    public boolean validate(TagDto tagDto) {
        return isStringParameterValid(tagDto.getName());
    }
}
