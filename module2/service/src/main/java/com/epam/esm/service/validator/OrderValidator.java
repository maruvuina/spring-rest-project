package com.epam.esm.service.validator;

import com.epam.esm.service.dto.OrderCreateDto;

/**
 * This is an interface for validation operations of Order entity.
 */
public interface OrderValidator {

    /**
     * Validate order create dto.
     *
     * @param orderCreateDto the order create dto
     */
    void validateOrderCreateDto(OrderCreateDto orderCreateDto);
}
