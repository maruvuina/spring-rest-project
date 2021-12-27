package com.epam.esm.dao.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_DESCRIPTION;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_NAME;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_TAG_NAME;

public final class DynamicQuery {

    private static final String SPACE = " ";
    private static final String SELECT_FROM = "select g from GiftCertificate g";
    private static final String PERCENT = "%";
    private static final String JOIN_GIFT_CERTIFICATE_TAG = "inner join g.tags t";
    private static final String GIFT_CERTIFICATE_NAME_COLUMN = "g.name";
    private static final String TAG_NAME_COLUMN = "t.name";
    private static final String GIFT_CERTIFICATE_DESCRIPTION_COLUMN = "g.description";
    private static final String WHERE = "where";
    private static final String AND = "and";
    private static final String LIKE_START = "like ";
    private static final String LIKE_END = "";
    private static final String ORDER_BY = "order by";
    private static final String COLON_OPERATOR = ":";

    private DynamicQuery() {}

    public static DynamicQueryResult retrieveQuery(GiftCertificateParameter giftCertificateParameter) {
        Map<String, String> parameters = new HashMap<>();
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
                    queryBuilder, COLUMN_LABEL_NAME, giftCertificateParameter.getName() + PERCENT);
        }
        if (isParameterValid(giftCertificateParameter.getDescription())) {
            fillSearchQuery(queryBuilder, GIFT_CERTIFICATE_DESCRIPTION_COLUMN, COLUMN_LABEL_DESCRIPTION);
            parameters = setSortOrderParameterForDataWithOneParameter(giftCertificateParameter,
                    queryBuilder, COLUMN_LABEL_DESCRIPTION, giftCertificateParameter.getDescription() + PERCENT);
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
            fillQueryForTwoParameters(queryBuilder, TAG_NAME_COLUMN,
                    GIFT_CERTIFICATE_NAME_COLUMN, COLUMN_LABEL_TAG_NAME, COLUMN_LABEL_NAME);
            setSortAndOrder(giftCertificateParameter, queryBuilder);
            parameters.put(COLUMN_LABEL_TAG_NAME, giftCertificateParameter.getTagName());
            parameters.put(COLUMN_LABEL_NAME, giftCertificateParameter.getName() + PERCENT);
        }
        if (isParameterValid(giftCertificateParameter.getTagName()) &&
                isParameterValid(giftCertificateParameter.getDescription())) {
            queryBuilder.append(JOIN_GIFT_CERTIFICATE_TAG + SPACE);
            fillQueryForTwoParameters(queryBuilder, TAG_NAME_COLUMN,
                    COLUMN_LABEL_DESCRIPTION, COLUMN_LABEL_TAG_NAME, COLUMN_LABEL_DESCRIPTION);
            setSortAndOrder(giftCertificateParameter, queryBuilder);
            parameters.put(COLUMN_LABEL_TAG_NAME, giftCertificateParameter.getTagName());
            parameters.put(COLUMN_LABEL_DESCRIPTION, giftCertificateParameter.getDescription() + PERCENT);
        }
        if (isParameterValid(giftCertificateParameter.getName()) &&
                isParameterValid(giftCertificateParameter.getDescription())) {
            fillQueryForTwoParameters(queryBuilder, GIFT_CERTIFICATE_NAME_COLUMN,
                    COLUMN_LABEL_DESCRIPTION, COLUMN_LABEL_NAME, COLUMN_LABEL_DESCRIPTION);
            setSortAndOrder(giftCertificateParameter, queryBuilder);
            parameters.put(COLUMN_LABEL_NAME, giftCertificateParameter.getName() + PERCENT);
            parameters.put(COLUMN_LABEL_DESCRIPTION, giftCertificateParameter.getDescription() + PERCENT);
        }
        return parameters;
    }

    private static void fillQueryForTwoParameters(StringBuilder queryBuilder, String firstColumnParameter,
                                                  String secondColumnParameter, String firstColumnLabel,
                                                  String secondColumnLabel) {
        queryBuilder.append(WHERE + SPACE)
                .append(firstColumnParameter)
                .append(SPACE + COLON_OPERATOR)
                .append(firstColumnLabel)
                .append(SPACE + AND + SPACE)
                .append(secondColumnParameter)
                .append(SPACE + LIKE_START + COLON_OPERATOR)
                .append(secondColumnLabel)
                .append(LIKE_END);
    }

    private static Map<String, String> retrieveDataForThreeParameters(GiftCertificateParameter giftCertificateParameter,
                                                               StringBuilder queryBuilder) {
        Map<String, String> parameters = new HashMap<>();
        if (isParameterValid(giftCertificateParameter.getTagName()) &&
                isParameterValid(giftCertificateParameter.getName()) &&
                isParameterValid(giftCertificateParameter.getDescription())) {
            queryBuilder.append(JOIN_GIFT_CERTIFICATE_TAG + SPACE);
            queryBuilder.append(WHERE + SPACE + TAG_NAME_COLUMN + SPACE + COLON_OPERATOR + COLUMN_LABEL_TAG_NAME)
                    .append(SPACE + AND + SPACE + GIFT_CERTIFICATE_NAME_COLUMN + SPACE + LIKE_START +
                            COLON_OPERATOR + COLUMN_LABEL_NAME + LIKE_END)
                    .append(SPACE + AND + SPACE + COLUMN_LABEL_DESCRIPTION + SPACE + LIKE_START +
                            COLON_OPERATOR + COLUMN_LABEL_DESCRIPTION + LIKE_END);
            setSortAndOrder(giftCertificateParameter, queryBuilder);
            parameters.put(GIFT_CERTIFICATE_NAME_COLUMN, giftCertificateParameter.getTagName());
            parameters.put(COLUMN_LABEL_NAME, giftCertificateParameter.getName() + PERCENT);
            parameters.put(COLUMN_LABEL_DESCRIPTION, giftCertificateParameter.getDescription() + PERCENT);
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
                .append(SPACE + LIKE_START + COLON_OPERATOR)
                .append(columnLabel)
                .append(LIKE_END);
    }

    private static <E extends Enum<E>> boolean isEnumValueValid(E clazz) {
        return clazz != null;
    }
}
