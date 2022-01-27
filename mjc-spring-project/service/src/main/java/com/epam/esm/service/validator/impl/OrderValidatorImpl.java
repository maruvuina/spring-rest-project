package com.epam.esm.service.validator.impl;

import com.epam.esm.service.dto.OrderCreateDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.OrderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.epam.esm.service.exception.ErrorCode.ERROR_301400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_302400;

@Slf4j
@Component
public class OrderValidatorImpl implements OrderValidator {

    @Override
    public void validate(OrderCreateDto orderCreateDto) {
        if (isIdParamInvalid(orderCreateDto.getUserId())) {
            log.error("User id = {} is invalid", orderCreateDto.getUserId());
            throw new ServiceException(ERROR_301400, String.valueOf(orderCreateDto.getUserId()));
        }
        if(isIdParamInvalid(orderCreateDto.getGiftCertificateId())) {
            log.error("Gift certificate id = {} is invalid", orderCreateDto.getGiftCertificateId());
            throw new ServiceException(ERROR_302400, String.valueOf(orderCreateDto.getGiftCertificateId()));
        }
    }

    private boolean isIdParamInvalid(Long id) {
        return id == null || id <= 0 || id >= Long.MAX_VALUE;
    }
}
