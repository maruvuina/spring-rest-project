package com.epam.esm.service.util;

import com.epam.esm.service.dto.DynamicQueryResult;
import com.epam.esm.service.dto.GiftCertificateParameter;
import com.epam.esm.service.exception.ServiceException;

import java.util.stream.Stream;

import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_CREATE_DATE;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_DESCRIPTION;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_DURATION;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_ID;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_LAST_UPDATE_DATE;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_NAME;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_PRICE;

public final class DynamicQuery {

    private static final String SPACE = " ";
    private static final String SELECT_FROM =
            "SELECT g.id, g.name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificate AS g";
    private static final String PERCENT = "%";
    private static final String JOIN_GIFT_CERTIFICATE_TAG =
            "INNER JOIN gift_certificate_tag AS gct ON g.id = gct.gift_certificate_id " +
                    "INNER JOIN tag AS t ON gct.tag_id = t.id";
    private static final String TAG_NAME_JOIN_COLUMN = "t.name";
    private static final String EQUAL_SIGN = "=";
    private static final String WHERE = "WHERE";
    private static final String ILIKE = "ILIKE";
    private static final String PREPARED_OPERATOR = "?";
    private static final String ORDER_BY = "ORDER BY";
    private static final String DESC = "desc";
    private static final String ASC = "asc";

    private DynamicQuery() {}

    public static DynamicQueryResult retrieveQuery(GiftCertificateParameter giftCertificateParameter) {
        String parameter = "";
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(SELECT_FROM + SPACE);
        if (isParameterValid(giftCertificateParameter.getTagName())) {
            queryBuilder.append(JOIN_GIFT_CERTIFICATE_TAG + SPACE);
            queryBuilder.append(WHERE + SPACE + TAG_NAME_JOIN_COLUMN + EQUAL_SIGN + PREPARED_OPERATOR);
            parameter = giftCertificateParameter.getTagName();
        }
        if (isParameterValid(giftCertificateParameter.getGiftCertificateName())) {
            parameter = searchQueryFill(queryBuilder, COLUMN_LABEL_NAME,
                    giftCertificateParameter.getGiftCertificateName());
        }
        if (isParameterValid(giftCertificateParameter.getDescription())) {
            parameter = searchQueryFill(queryBuilder, COLUMN_LABEL_DESCRIPTION,
                    giftCertificateParameter.getDescription());
        }
        if (isParameterValid(giftCertificateParameter.getSort()) &&
                isParameterValid(giftCertificateParameter.getOrder())) {
            if (!isSortAndOrderParameterSatisfied(giftCertificateParameter.getSort(),
                    giftCertificateParameter.getOrder())) {
                throw new ServiceException("Sort parameters not valid");
            }
            queryBuilder.append(ORDER_BY).append(SPACE);
            queryBuilder.append(giftCertificateParameter.getSort());
            queryBuilder.append(SPACE).append(giftCertificateParameter.getOrder());
        }
        return new DynamicQueryResult(queryBuilder.toString(), parameter);
    }

    private static boolean isParameterValid(String parameter) {
        return parameter != null && !parameter.isBlank();
    }

    private static String searchQueryFill(StringBuilder queryBuilder, String columnLabel, String searchValue) {
        queryBuilder.append(WHERE + SPACE)
                .append(columnLabel)
                .append(SPACE)
                .append(ILIKE)
                .append(SPACE)
                .append(PREPARED_OPERATOR);
        return searchValue + PERCENT;
    }

    private static boolean isSortAndOrderParameterSatisfied(String sortParameter, String orderParameter) {
        return isSortValueSatisfied(sortParameter) && isOrderValueSatisfied(orderParameter);
    }

    private static boolean isSortValueSatisfied(String value) {
        Stream<String> sortTypes = Stream.of(COLUMN_LABEL_ID, COLUMN_LABEL_NAME,
                COLUMN_LABEL_DESCRIPTION, COLUMN_LABEL_PRICE, COLUMN_LABEL_DURATION,
                COLUMN_LABEL_CREATE_DATE, COLUMN_LABEL_LAST_UPDATE_DATE);
        return isStringValueSatisfied(sortTypes, value);
    }

    private static boolean isOrderValueSatisfied(String value) {
        Stream<String> orderTypes = Stream.of(DESC, ASC);
        return isStringValueSatisfied(orderTypes, value);
    }

    private static boolean isStringValueSatisfied(Stream<String> stringStream, String value) {
        return stringStream.anyMatch(value::equalsIgnoreCase);
    }
}
