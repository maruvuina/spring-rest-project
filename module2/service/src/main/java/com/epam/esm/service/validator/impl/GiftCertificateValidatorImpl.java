package com.epam.esm.service.validator.impl;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.validator.GiftCertificateValidator;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateValidatorImpl implements GiftCertificateValidator {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @Override
    public boolean validate(GiftCertificateDto giftCertificateDto) {
        return isStringParameterValid(giftCertificateDto.getName()) &&
                isStringParameterValid(giftCertificateDto.getDescription()) &&
                isPositive(giftCertificateDto.getPrice().intValue()) &&
                isPositive(giftCertificateDto.getDuration()) &&
                isValidDate(giftCertificateDto.getCreateDate()) &&
                isValidDate(giftCertificateDto.getLastUpdateDate());
    }

    @Override
    public List<Boolean> validateUpdatedGiftCertificateDto(GiftCertificateDto giftCertificateDto) {
        List<Boolean> booleanList = new ArrayList<>();
        if (giftCertificateDto.getName() != null) {
            booleanList.add(isStringParameterValid(giftCertificateDto.getName()));
        }
        if (giftCertificateDto.getDescription() != null) {
            booleanList.add(isStringParameterValid(giftCertificateDto.getDescription()));
        }
        if (giftCertificateDto.getPrice() != null) {
            booleanList.add(isPositive(giftCertificateDto.getPrice().intValue()));
        }
        if (giftCertificateDto.getDuration() != null) {
            booleanList.add(isPositive(giftCertificateDto.getDuration()));
        }
        if (giftCertificateDto.getCreateDate() != null) {
            booleanList.add(isValidDate(giftCertificateDto.getCreateDate()));
        }
        if (giftCertificateDto.getLastUpdateDate() != null) {
            booleanList.add(isValidDate(giftCertificateDto.getLastUpdateDate()));
        }
        return booleanList;
    }

    private boolean isPositive(Integer value) {
        return value != null && value > 0;
    }

    private boolean isValidDate(String date) {
        return isStringParameterValid(date) && isValidDateFormat(date);
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
