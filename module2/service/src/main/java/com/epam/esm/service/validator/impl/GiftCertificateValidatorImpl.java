package com.epam.esm.service.validator.impl;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.GiftCertificateValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.epam.esm.service.exception.ErrorCode.ERROR_107400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_108400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_109400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_110400;

@Slf4j
@Component
public class GiftCertificateValidatorImpl implements GiftCertificateValidator {

    private static final Integer STRING_MIN_LENGTH = 4;
    private static final Integer STRING_MAX_LENGTH = 255;
    private static final Integer MIN_PRICE = 1;
    private static final Integer MAX_PRICE = 20_000;
    private static final Integer MIN_DURATION = 1;
    private static final Integer MAX_DURATION = 365;

    @Override
    public void validateName(String name) {
        if (name == null || name.isBlank() ||
                !(name.length() >= STRING_MIN_LENGTH && name.length() <= STRING_MAX_LENGTH)) {
            log.error("Invalid gift certificate name = {}", name);
            throw new ServiceException(ERROR_107400, name);
        }
    }

    @Override
    public void validate(GiftCertificateDto giftCertificateDto) {
        validateIdWhenCreate(giftCertificateDto.getId());
        validateName(giftCertificateDto.getName());
        validateDescription(giftCertificateDto.getDescription());
        validatePrice(giftCertificateDto.getPrice().longValue());
        validateDuration(giftCertificateDto.getDuration());
    }

    @Override
    public void validateDataToUpdate(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getName() != null) {
            validateName(giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null) {
            validateDescription(giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != null) {
            validatePrice(giftCertificateDto.getPrice().longValue());
        }
        if (giftCertificateDto.getDuration() != null) {
            validateDuration(giftCertificateDto.getDuration());
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.isBlank() ||
                !(description.length() >= STRING_MIN_LENGTH && description.length() <= STRING_MAX_LENGTH)) {
            log.error("Invalid gift certificate description = {}", description);
            throw new ServiceException(ERROR_108400, description);
        }
    }

    private void validatePrice(Long price) {
        if (!(price >= MIN_PRICE && price <= MAX_PRICE)) {
            log.error("Invalid gift certificate price = {}", price);
            throw new ServiceException(ERROR_109400, String.valueOf(price));
        }
    }

    private void validateDuration(Integer duration) {
        if (!(duration >= MIN_DURATION && duration <= MAX_DURATION)) {
            log.error("Invalid gift certificate duration = {}", duration);
            throw new ServiceException(ERROR_110400, String.valueOf(duration));
        }
    }
}
