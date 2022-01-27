package com.epam.esm.web.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ExceptionMessageTranslator {

    private final ResourceBundleMessageSource resourceBundleMessageSource;

    public String translateToLocale(String messageCode) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        return resourceBundleMessageSource.getMessage(messageCode, null, currentLocale);
    }
}
