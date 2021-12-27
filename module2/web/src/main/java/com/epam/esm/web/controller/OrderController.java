package com.epam.esm.web.controller;

import com.epam.esm.dao.util.Page;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Order controller.
 */
@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    /**
     * Create order.
     *
     * @param orderDto the order dto
     * @return the order dto
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public OrderDto create(@Valid @RequestBody OrderDto orderDto) {
        return orderService.create(orderDto);
    }

    /**
     * Retrieve all orders.
     *
     * @param page the page
     * @param size the size
     * @return the list of order dto
     */
    @GetMapping
    public List<OrderDto> retrieveAll(@RequestParam(defaultValue = "0") @Min(0) @Max(Integer.MAX_VALUE) Integer page,
                                      @RequestParam(defaultValue = "3") @Min(1) @Max(Integer.MAX_VALUE) Integer size) {
        return orderService.retrieveAll(new Page(page, size));
    }

    /**
     * Retrieve order by id.
     *
     * @param id the id
     * @return the order dto
     */
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public OrderDto retrieveById(@PathVariable("id") @Min(1) @Max(Long.MAX_VALUE) Long id) {
        return orderService.retrieveById(id);
    }
}
