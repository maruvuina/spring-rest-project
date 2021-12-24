package com.epam.esm.dao.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_DESCRIPTION;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_NAME;

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
    private static final String GIFT_CERTIFICATE_NAME_JOIN_COLUMN = "g.name";
    private static final String EQUAL_SIGN = "=";
    private static final String WHERE = "WHERE";
    private static final String AND = "AND";
    private static final String ILIKE = "ILIKE";
    private static final String PREPARED_OPERATOR = "?";
    private static final String ORDER_BY = "ORDER BY";

    private DynamicQuery() {}

    public static DynamicQueryResult retrieveQuery(GiftCertificateParameter giftCertificateParameter) {
        List<String> parameters = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(SELECT_FROM + SPACE);
        ParameterCountType parameterCountType = retrieveParameterCount(giftCertificateParameter);
        switch (parameterCountType) {
            case ONE:
                parameters = retrieveDataForOneParameter(giftCertificateParameter, queryBuilder);
                break;
            case TWO:
                parameters = retrieveDataForTwoParameters(giftCertificateParameter, queryBuilder);
                break;
            case THREE:
                parameters = retrieveDataForThreeParameters(giftCertificateParameter, queryBuilder);
                break;
            case NON:
                parameters = retrieveDataForNonParameters(giftCertificateParameter, queryBuilder);
                break;
        }
        return new DynamicQueryResult(queryBuilder.toString(), parameters);
    }
    
    private static ParameterCountType retrieveParameterCount(GiftCertificateParameter giftCertificateParameter) {
        int one = 1;
        int two = 2;
        int three = 3;
        List<String> list = new ArrayList<>();
        list.add(giftCertificateParameter.getTagName());
        list.add(giftCertificateParameter.getName());
        list.add(giftCertificateParameter.getDescription());
        int count = (int) list.stream().filter(Objects::nonNull).count();
        ParameterCountType parameterCountType = ParameterCountType.NON;
        if (count == one) {
            parameterCountType = ParameterCountType.ONE;
        } else if (count == two) {
            parameterCountType = ParameterCountType.TWO;
        } else if (count == three) {
            parameterCountType = ParameterCountType.THREE;
        }
        return parameterCountType;
    }

    private static List<String> retrieveDataForOneParameter(GiftCertificateParameter giftCertificateParameter,
                                                            StringBuilder queryBuilder) {
        List<String> parameters = new ArrayList<>();
        if (isParameterValid(giftCertificateParameter.getTagName())) {
            queryBuilder.append(JOIN_GIFT_CERTIFICATE_TAG + SPACE);
            queryBuilder.append(WHERE + SPACE + TAG_NAME_JOIN_COLUMN + EQUAL_SIGN + PREPARED_OPERATOR);
            parameters = setSortOrderParameterForDataWithOneParameter(giftCertificateParameter,
                    queryBuilder, giftCertificateParameter.getTagName());
        }
        if (isParameterValid(giftCertificateParameter.getName())) {
            fillSearchQuery(queryBuilder, COLUMN_LABEL_NAME);
            parameters = setSortOrderParameterForDataWithOneParameter(giftCertificateParameter,
                    queryBuilder, giftCertificateParameter.getName() + PERCENT);
        }
        if (isParameterValid(giftCertificateParameter.getDescription())) {
            fillSearchQuery(queryBuilder, COLUMN_LABEL_DESCRIPTION);
            parameters = setSortOrderParameterForDataWithOneParameter(giftCertificateParameter,
                    queryBuilder, giftCertificateParameter.getDescription() + PERCENT);
        }
        return parameters;
    }

    private static List<String> setSortOrderParameterForDataWithOneParameter(GiftCertificateParameter giftCertificateParameter,
                   StringBuilder queryBuilder, String parameter) {
        List<String> parameters = new ArrayList<>();
        setSortAndOrder(giftCertificateParameter, queryBuilder);
        parameters.add(parameter);
        return parameters;
    }

    private static void setSortAndOrder(GiftCertificateParameter giftCertificateParameter, StringBuilder queryBuilder) {
        if (isEnumValueValid(giftCertificateParameter.getSort())) {
            queryBuilder.append(SPACE + ORDER_BY + SPACE).append(giftCertificateParameter.getSort().getValue());
            setOrder(giftCertificateParameter, queryBuilder);
        }
    }

    private static void setOrder(GiftCertificateParameter giftCertificateParameter, StringBuilder queryBuilder) {
        if (isEnumValueValid(giftCertificateParameter.getOrder())) {
            queryBuilder.append(SPACE).append(giftCertificateParameter.getOrder().getValue());
        }
    }

    private static List<String> retrieveDataForTwoParameters(GiftCertificateParameter giftCertificateParameter,
                                                             StringBuilder queryBuilder) {
        List<String> parameters = new ArrayList<>();
        if (isParameterValid(giftCertificateParameter.getTagName()) &&
                isParameterValid(giftCertificateParameter.getName())) {
            queryBuilder.append(JOIN_GIFT_CERTIFICATE_TAG + SPACE);
            fillQueryForTwoParameters(queryBuilder, TAG_NAME_JOIN_COLUMN, GIFT_CERTIFICATE_NAME_JOIN_COLUMN);
            parameters = setSortOrderParameterForDataWithTwoParameters(giftCertificateParameter, queryBuilder,
                    giftCertificateParameter.getTagName(), giftCertificateParameter.getName() + PERCENT);
        }
        if (isParameterValid(giftCertificateParameter.getTagName()) &&
                isParameterValid(giftCertificateParameter.getDescription())) {
            queryBuilder.append(JOIN_GIFT_CERTIFICATE_TAG + SPACE);
            fillQueryForTwoParameters(queryBuilder, TAG_NAME_JOIN_COLUMN, COLUMN_LABEL_DESCRIPTION);
            parameters = setSortOrderParameterForDataWithTwoParameters(giftCertificateParameter, queryBuilder,
                    giftCertificateParameter.getTagName(),
                    giftCertificateParameter.getDescription() + PERCENT);
        }
        if (isParameterValid(giftCertificateParameter.getName()) &&
                isParameterValid(giftCertificateParameter.getDescription())) {
            fillQueryForTwoParameters(queryBuilder, GIFT_CERTIFICATE_NAME_JOIN_COLUMN, COLUMN_LABEL_DESCRIPTION);
            parameters = setSortOrderParameterForDataWithTwoParameters(giftCertificateParameter, queryBuilder,
                    giftCertificateParameter.getName(),
                    giftCertificateParameter.getDescription() + PERCENT);
        }
        return parameters;
    }

    private static List<String> setSortOrderParameterForDataWithTwoParameters(GiftCertificateParameter giftCertificateParameter,
                          StringBuilder queryBuilder, String firstParameter, String secondParameter) {
        setSortAndOrder(giftCertificateParameter, queryBuilder);
        return setParametersForQueryWithTwoParameters(firstParameter, secondParameter);
    }

    private static List<String> setParametersForQueryWithTwoParameters(String firstParameter, String secondParameter) {
        List<String> parameters = new ArrayList<>();
        parameters.add(firstParameter);
        parameters.add(secondParameter);
        return parameters;
    }

    private static void fillQueryForTwoParameters(StringBuilder queryBuilder, String firstColumnParameter,
                                                  String secondColumnParameter) {
        queryBuilder.append(WHERE + SPACE)
                .append(firstColumnParameter)
                .append(SPACE + EQUAL_SIGN + SPACE + PREPARED_OPERATOR)
                .append(SPACE + AND + SPACE)
                .append(secondColumnParameter)
                .append(SPACE + ILIKE + SPACE + PREPARED_OPERATOR);
    }

    private static List<String> retrieveDataForThreeParameters(GiftCertificateParameter giftCertificateParameter,
                                                               StringBuilder queryBuilder) {
        List<String> parameters = new ArrayList<>();
        if (isParameterValid(giftCertificateParameter.getTagName()) &&
                isParameterValid(giftCertificateParameter.getName()) &&
                isParameterValid(giftCertificateParameter.getDescription())) {
            queryBuilder.append(JOIN_GIFT_CERTIFICATE_TAG + SPACE);
            queryBuilder.append(WHERE + SPACE + TAG_NAME_JOIN_COLUMN + SPACE + EQUAL_SIGN + SPACE + PREPARED_OPERATOR)
                    .append(SPACE + AND + SPACE + GIFT_CERTIFICATE_NAME_JOIN_COLUMN + SPACE + ILIKE + SPACE +
                            PREPARED_OPERATOR)
                    .append(SPACE + AND + SPACE + COLUMN_LABEL_DESCRIPTION + SPACE + ILIKE + SPACE + PREPARED_OPERATOR);
            setSortAndOrder(giftCertificateParameter, queryBuilder);
            parameters.add(giftCertificateParameter.getTagName());
            parameters.add(giftCertificateParameter.getName() + PERCENT);
            parameters.add(giftCertificateParameter.getDescription() + PERCENT);
        }
        return parameters;
    }

    private static List<String> retrieveDataForNonParameters(GiftCertificateParameter giftCertificateParameter,
                                                             StringBuilder queryBuilder) {
        List<String> parameters = new ArrayList<>();
        if (isEnumValueValid(giftCertificateParameter.getSort())) {
            queryBuilder.append(SPACE + ORDER_BY + SPACE).append(giftCertificateParameter.getSort().getValue());
            setOrder(giftCertificateParameter, queryBuilder);
        }
        return parameters;
    }

    private static boolean isParameterValid(String parameter) {
        return parameter != null && !parameter.isBlank();
    }

    private static void fillSearchQuery(StringBuilder queryBuilder, String columnLabel) {
        queryBuilder.append(WHERE + SPACE)
                .append(columnLabel)
                .append(SPACE + ILIKE + SPACE + PREPARED_OPERATOR);
    }

    private static <E extends Enum<E>> boolean isEnumValueValid(E clazz) {
        return clazz != null;
    }
}
