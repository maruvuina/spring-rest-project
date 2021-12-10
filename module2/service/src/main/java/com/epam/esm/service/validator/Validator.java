package com.epam.esm.service.validator;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class Validator {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public void validatedId(Long id) {
        validateParam(id);
    }

    public void validateString(String name) {
        validateParam(name);
    }

    public void validatedTagDto(TagDto tagDto) {
        isStringParameterValid(tagDto.getName());
    }

    public void validatedGiftCertificateDto(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getName() != null) {
            isStringParameterValid(giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null) {
            isStringParameterValid(giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != null) {
            isPositive(giftCertificateDto.getPrice().intValue());
        }
        if (giftCertificateDto.getDuration() != null) {
            isPositive(giftCertificateDto.getDuration());
        }
        if (giftCertificateDto.getCreateDate() != null) {
            isValidDate(giftCertificateDto.getCreateDate());
        }
        if (giftCertificateDto.getLastUpdateDate() != null) {
            isValidDate(giftCertificateDto.getLastUpdateDate());
        }
    }

    private void validateParam(Object param) {
        if (param == null) {
            throw new ServiceException("Null value passed");
        }
    }

    private void isStringParameterValid(String parameter) {
        if (parameter.isEmpty()) {
            throw new ServiceException("Incorrect string value");
        }
    }

    private void isPositive(Integer value) {
        if (value <= 0) {
            throw new ServiceException("Value is less than zero");
        }
    }

    private void isValidDate(String date) {
        if (date != null && !date.isBlank()) {
            if (!isValidDateFormat(date)) {
                throw new ServiceException("Date format is incorrect");
            }
        }
    }

    private boolean isValidDateFormat(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
}
