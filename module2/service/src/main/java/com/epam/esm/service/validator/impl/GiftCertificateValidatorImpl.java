package com.epam.esm.service.validator.impl;

import com.epam.esm.dao.util.GiftCertificateParameter;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.GiftCertificateValidator;
import com.epam.esm.service.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.epam.esm.service.exception.ErrorCode.ERROR_101400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_102400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_103400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_104400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_106400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_107400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_109400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_110400;

@Slf4j
@Component
@RequiredArgsConstructor
public class GiftCertificateValidatorImpl implements GiftCertificateValidator {

    private static final Integer STRING_MIN_LENGTH = 4;
    private static final Integer STRING_MAX_LENGTH = 255;
    private static final Integer MIN_PRICE = 1;
    private static final Integer MAX_PRICE = 20_000;
    private static final Integer MIN_DURATION = 1;
    private static final Integer MAX_DURATION = 365;

    private final TagValidator tagValidator;

    @Override
    public void validateName(String name) {
        if (StringUtils.isBlank(name) ||
                !(name.length() >= STRING_MIN_LENGTH && name.length() <= STRING_MAX_LENGTH)) {
            log.error("Invalid gift certificate name = {}", name);
            throw new ServiceException(ERROR_101400, name);
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
    public void validateDataToCreate(GiftCertificateDto giftCertificateDto) {
        validateIdWhenCreate(giftCertificateDto.getId());
        validateDate(giftCertificateDto);
        if (giftCertificateDto.getTags() == null) {
            log.error("GiftCertificate list tag are null");
            throw new ServiceException(ERROR_109400);
        }
        validateTags(giftCertificateDto.getTags());
        validate(giftCertificateDto);
    }

    @Override
    public void validateDataToUpdate(GiftCertificateDto giftCertificateDto) {
        validateIdWhenCreate(giftCertificateDto.getId());
        validateDate(giftCertificateDto);
        if (giftCertificateDto.getTags() != null) {
            validateTags(giftCertificateDto.getTags());
        }
        validateUpdate(giftCertificateDto);
    }

    @Override
    public void validateGiftCertificateParameter(GiftCertificateParameter giftCertificateParameter) {
        if (giftCertificateParameter.getTagName() != null) {
            giftCertificateParameter.getTagName().forEach(this::validateSearchParameter);
        }
        if (giftCertificateParameter.getName() != null) {
            validateSearchParameter(giftCertificateParameter.getName());
        }
        if (giftCertificateParameter.getDescription() != null) {
            validateSearchParameter(giftCertificateParameter.getDescription());
        }
    }

    private void validateUpdate(GiftCertificateDto giftCertificateDto) {
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

    private void validateDate(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getCreateDate() != null &&
                giftCertificateDto.getLastUpdateDate() != null) {
            log.error("Fields create date and last update date cannot be modified via request body");
            throw new ServiceException(ERROR_110400);
        }
        if (giftCertificateDto.getCreateDate() != null) {
            log.error("Field create date cannot be modified via request body");
            throw new ServiceException(ERROR_106400);
        }
        if (giftCertificateDto.getLastUpdateDate() != null) {
            log.error("Field last update date cannot be modified via request body");
            throw new ServiceException(ERROR_107400);
        }
    }

    private void validateDescription(String description) {
        if (StringUtils.isBlank(description) ||
                !(description.length() >= STRING_MIN_LENGTH && description.length() <= STRING_MAX_LENGTH)) {
            log.error("Invalid gift certificate description = {}", description);
            throw new ServiceException(ERROR_102400, description);
        }
    }

    private void validatePrice(Long price) {
        if (!(price >= MIN_PRICE && price <= MAX_PRICE)) {
            log.error("Invalid gift certificate price = {}", price);
            throw new ServiceException(ERROR_103400, String.valueOf(price));
        }
    }

    private void validateDuration(Integer duration) {
        if (!(duration >= MIN_DURATION && duration <= MAX_DURATION)) {
            log.error("Invalid gift certificate duration = {}", duration);
            throw new ServiceException(ERROR_104400, String.valueOf(duration));
        }
    }

    private void validateTags(List<TagDto> tagDtoList) {
        tagDtoList.forEach(tagValidator::validate);
    }

    private void validateSearchParameter(String parameter) {
        if (StringUtils.isBlank(parameter) || parameter.length() < 1) {
            log.error("Invalid gift certificate parameter = {}", parameter);
            throw new ServiceException(ERROR_101400, parameter);
        }
    }
}
