package com.epam.esm.service.validator;

/**
 * This is an interface for basic validation operations.
 *
 * @param <T> the type of dto entity
 */
public interface Validator<T> {

    /**
     * Validated id.
     *
     * @param id the id
     * @return the boolean
     */
    default boolean validatedId(Long id) {
        return id != null;
    }

    /**
     * Validate string.
     *
     * @param string the name
     * @return the boolean
     */
    default boolean validateString(String string) {
        return isStringParameterValid(string);
    }

    /**
     * Is string parameter valid.
     *
     * @param parameter the parameter
     * @return the boolean
     */
    default boolean isStringParameterValid(String parameter) {
        return parameter != null && !parameter.isBlank();
    }

    /**
     * Validate boolean.
     *
     * @param t the dto entity
     * @return the boolean
     */
    boolean validate(T t);
}
