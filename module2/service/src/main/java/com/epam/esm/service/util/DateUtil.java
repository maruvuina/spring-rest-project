package com.epam.esm.service.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class DateUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final String TIME_ZONE = "UTC";

    private DateUtil() {}

    public static String retrieveFormatterDate(Instant instant) {
        return DateTimeFormatter
                .ofPattern(DATE_FORMAT)
                .withZone(ZoneId.of(TIME_ZONE))
                .format(Instant.parse(instant.toString()));
    }

    public static Instant retrieveDate() {
        return Instant.now();
    }
}
