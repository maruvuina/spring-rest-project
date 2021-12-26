package com.epam.esm.dao.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_DESCRIPTION;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_NAME;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_TAG_ID;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_TAG_NAME;

public final class DynamicQuery {

    private static final String SPACE = " ";
    private static final String SELECT_FROM = "select g from GiftCertificate g";
    private static final String PERCENT = "%";
    private static final String JOIN_GIFT_CERTIFICATE_TAG = "inner join g.tags t";
    private static final String GIFT_CERTIFICATE_NAME_COLUMN = "g.name";
    private static final String TAG_NAME_COLUMN = "t.name";
    private static final String GIFT_CERTIFICATE_NAME_COLUMN = "g.name";
    private static final String GIFT_CERTIFICATE_DESCRIPTION_COLUMN = "g.description";
    private static final String EQUAL_SIGN = "=";
    private static final String WHERE = "where";
    private static final String AND = "and";
    private static final String ILIKE = "ILIKE";
    private static final String PREPARED_OPERATOR = "?";
    private static final String ORDER_BY = "order by";
    private static final String COLON_OPERATOR = ":";

    private DynamicQuery() {}

    public static DynamicQueryResult retrieveQuery(GiftCertificateParameter giftCertificateParameter) {
        Map<String, Object> parameters = new HashMap<>();
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

    private static Map<String, String> retrieveDataForOneParameter(GiftCertificateParameter giftCertificateParameter,
                                                            StringBuilder queryBuilder) {
        Map<String, String> parameters = new HashMap<>();
        if (isParameterValid(giftCertificateParameter.getTagName())) {
            queryBuilder.append(JOIN_GIFT_CERTIFICATE_TAG + SPACE);
            queryBuilder.append(WHERE + SPACE + TAG_NAME_COLUMN + SPACE + COLON_OPERATOR + COLUMN_LABEL_NAME);
            parameters = setSortOrderParameterForDataWithOneParameter(giftCertificateParameter,
                    queryBuilder, COLUMN_LABEL_NAME, giftCertificateParameter.getTagName());
        }
        if (isParameterValid(giftCertificateParameter.getName())) {
            fillSearchQuery(queryBuilder, GIFT_CERTIFICATE_NAME_COLUMN, COLUMN_LABEL_NAME);
            parameters = setSortOrderParameterForDataWithOneParameter(giftCertificateParameter,
                    queryBuilder, COLUMN_LABEL_NAME, giftCertificateParameter.getName());
        }
        if (isParameterValid(giftCertificateParameter.getDescription())) {
            fillSearchQuery(queryBuilder, GIFT_CERTIFICATE_DESCRIPTION_COLUMN, COLUMN_LABEL_DESCRIPTION);
            parameters = setSortOrderParameterForDataWithOneParameter(giftCertificateParameter,
                    queryBuilder, COLUMN_LABEL_DESCRIPTION, giftCertificateParameter.getDescription());
        }
        return parameters;
    }

    private static Map<String, String> setSortOrderParameterForDataWithOneParameter(GiftCertificateParameter giftCertificateParameter,
                   StringBuilder queryBuilder, String columnLabel, String parameter) {
        Map<String, String> parameters = new HashMap<>();
        setSortAndOrder(giftCertificateParameter, queryBuilder);
        parameters.put(columnLabel, parameter);
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

    private static Map<String, String> retrieveDataForTwoParameters(GiftCertificateParameter giftCertificateParameter,
                                                             StringBuilder queryBuilder) {
        Map<String, String> parameters = new HashMap<>();
        if (isParameterValid(giftCertificateParameter.getTagName()) &&
                isParameterValid(giftCertificateParameter.getName())) {
            queryBuilder.append(JOIN_GIFT_CERTIFICATE_TAG + SPACE);
            fillQueryForTwoParameters(queryBuilder, TAG_NAME_COLUMN, GIFT_CERTIFICATE_NAME_COLUMN, COLUMN_LABEL_TAG_NAME, COLUMN_LABEL_NAME);
            parameters = setSortOrderParameterForDataWithTwoParameters(giftCertificateParameter, queryBuilder,
                    giftCertificateParameter.getTagName(), giftCertificateParameter.getName() + PERCENT);
        }
        if (isParameterValid(giftCertificateParameter.getTagName()) &&
                isParameterValid(giftCertificateParameter.getDescription())) {
            queryBuilder.append(JOIN_GIFT_CERTIFICATE_TAG + SPACE);
            fillQueryForTwoParameters(queryBuilder, TAG_NAME_COLUMN, COLUMN_LABEL_DESCRIPTION);
            parameters = setSortOrderParameterForDataWithTwoParameters(giftCertificateParameter, queryBuilder,
                    giftCertificateParameter.getTagName(),
                    giftCertificateParameter.getDescription() + PERCENT);
        }
        if (isParameterValid(giftCertificateParameter.getName()) &&
                isParameterValid(giftCertificateParameter.getDescription())) {
            fillQueryForTwoParameters(queryBuilder, GIFT_CERTIFICATE_NAME_COLUMN, COLUMN_LABEL_DESCRIPTION);
            parameters = setSortOrderParameterForDataWithTwoParameters(giftCertificateParameter, queryBuilder,
                    giftCertificateParameter.getName(),
                    giftCertificateParameter.getDescription() + PERCENT);
        }
        return parameters;
    }

    private static Map<String, String> setSortOrderParameterForDataWithTwoParameters(GiftCertificateParameter giftCertificateParameter,
                          StringBuilder queryBuilder, String firstParameter, String secondParameter) {
        setSortAndOrder(giftCertificateParameter, queryBuilder);
        return setParametersForQueryWithTwoParameters(firstParameter, secondParameter);
    }

    private static Map<String, String> setParametersForQueryWithTwoParameters(String firstParameter, String secondParameter) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(firstParameter);
        parameters.put(secondParameter);
        return parameters;
    }

    private static void fillQueryForTwoParameters(StringBuilder queryBuilder, String firstColumnParameter,
                                                  String secondColumnParameter, String firstColumnLabel,
                                                  String secondColumnLabel) {
        queryBuilder.append(WHERE + SPACE)
                .append(firstColumnParameter)
                .append(SPACE + COLON_OPERATOR + firstColumnLabel)
                .append(SPACE + AND + SPACE)
                .append(secondColumnParameter)
                .append(SPACE + ILIKE + SPACE + PERCENT + COLON_OPERATOR + secondColumnLabel + PERCENT);
    }

    private static Map<String, String> retrieveDataForThreeParameters(GiftCertificateParameter giftCertificateParameter,
                                                               StringBuilder queryBuilder) {
        Map<String, Object> parameters = new HashMap<>();
        if (isParameterValid(giftCertificateParameter.getTagName()) &&
                isParameterValid(giftCertificateParameter.getName()) &&
                isParameterValid(giftCertificateParameter.getDescription())) {
            queryBuilder.append(JOIN_GIFT_CERTIFICATE_TAG + SPACE);
            queryBuilder.append(WHERE + SPACE + TAG_NAME_COLUMN + SPACE + EQUAL_SIGN + SPACE + PREPARED_OPERATOR)
                    .append(SPACE + AND + SPACE + GIFT_CERTIFICATE_NAME_COLUMN + SPACE + ILIKE + SPACE +
                            PREPARED_OPERATOR)
                    .append(SPACE + AND + SPACE + COLUMN_LABEL_DESCRIPTION + SPACE + ILIKE + SPACE + PREPARED_OPERATOR);
            setSortAndOrder(giftCertificateParameter, queryBuilder);
            parameters.put(giftCertificateParameter.getTagName());
            parameters.put(giftCertificateParameter.getName() + PERCENT);
            parameters.put(giftCertificateParameter.getDescription() + PERCENT);
        }
        return parameters;
    }

    private static Map<String, String> retrieveDataForNonParameters(GiftCertificateParameter giftCertificateParameter,
                                                             StringBuilder queryBuilder) {
        Map<String, String> parameters = new HashMap<>();
        if (isEnumValueValid(giftCertificateParameter.getSort())) {
            queryBuilder.append(SPACE + ORDER_BY + SPACE).append(giftCertificateParameter.getSort().getValue());
            setOrder(giftCertificateParameter, queryBuilder);
        }
        return parameters;
    }

    private static boolean isParameterValid(String parameter) {
        return parameter != null && !parameter.isBlank();
    }

    private static void fillSearchQuery(StringBuilder queryBuilder, String columnName, String columnLabel) {
        queryBuilder.append(WHERE + SPACE)
                .append(columnName)
                .append(SPACE + ILIKE + SPACE + PERCENT + COLON_OPERATOR + columnLabel + PERCENT);
    }

    private static <E extends Enum<E>> boolean isEnumValueValid(E clazz) {
        return clazz != null;
    }
}
