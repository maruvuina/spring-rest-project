package com.epam.esm.service.validator;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class Validator {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public boolean validatedId(Long id) {
        return id != null;
    }

    public boolean validateString(String name) {
        return isStringParameterValid(name);
    }

    public boolean validatedTagDto(TagDto tagDto) {
        return isStringParameterValid(tagDto.getName());
    }

    public boolean validatedGiftCertificateDto(GiftCertificateDto giftCertificateDto) {
        return isStringParameterValid(giftCertificateDto.getName()) &&
                isStringParameterValid(giftCertificateDto.getDescription()) &&
                isPositive(giftCertificateDto.getPrice().intValue()) &&
                isPositive(giftCertificateDto.getDuration()) &&
                isValidDate(giftCertificateDto.getCreateDate()) &&
                isValidDate(giftCertificateDto.getLastUpdateDate());
    }

    public List<Boolean> validatedUpdatedGiftCertificateDto(GiftCertificateDto giftCertificateDto) {
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

    private boolean isStringParameterValid(String parameter) {
        return parameter != null && !parameter.isBlank();
    }

    private boolean isPositive(Integer value) {
        return value > 0;
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
