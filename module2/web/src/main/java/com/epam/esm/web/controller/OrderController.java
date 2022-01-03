package com.epam.esm.web.controller;

import com.epam.esm.dao.util.Page;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderCreateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
     * @param orderCreateDto the order create dto
     * @return the order retrieve dto
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public OrderDto create(@Valid @RequestBody OrderCreateDto orderCreateDto) {
        OrderDto createdOrderDto = orderService.create(orderCreateDto);
        return addSelfLink(createdOrderDto, createdOrderDto.getId());
    }

    /**
     * Retrieve all orders.
     *
     * @param pageNumber the page number
     * @param size the size
     * @return the collection model of order dto
     */
    @GetMapping
    public CollectionModel<OrderDto> retrieveAll(@RequestParam(defaultValue = "0", name = "page")
                                      @Min(0) @Max(Integer.MAX_VALUE) Integer pageNumber,
                                      @RequestParam(defaultValue = "3")
                                      @Min(1) @Max(Integer.MAX_VALUE) Integer size) {
        return addSelfLinkToList(orderService.retrieveAll(new Page(pageNumber, size)));
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
        return addSelfLink(orderService.retrieveById(id), id);
    }

    /**
     * Retrieve order by user id order.
     *
     * @param userId the user id
     * @return the collection model of order dto
     */
    @GetMapping("/users/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CollectionModel<OrderDto> retrieveByUserId(@PathVariable("id") @Min(1) @Max(Long.MAX_VALUE) Long userId,
                                           @RequestParam(defaultValue = "0", name = "page")
                                           @Min(0) @Max(Integer.MAX_VALUE) Integer pageNumber,
                                           @RequestParam(defaultValue = "3")
                                           @Min(1) @Max(Integer.MAX_VALUE) Integer size) {
        return addSelfLinkToList(orderService.retrieveByUserId(userId, new Page(pageNumber, size)));
    }

    private CollectionModel<OrderDto> addSelfLinkToList(List<OrderDto> orderDtoList) {
        orderDtoList.forEach(orderDto -> {
            Link selfLink = linkTo(methodOn(OrderController.class)
                    .retrieveById(orderDto.getId())).withSelfRel();
            orderDto.add(selfLink);
            setLinkToGiftCertificateDto(orderDto.getGiftCertificateDto());
            addSelfLinkToTagDtoList(orderDto.getGiftCertificateDto().getTags());
        });
        Link link = linkTo(OrderController.class).withSelfRel();
        return CollectionModel.of(orderDtoList, link);
    }

    private void setLinkToGiftCertificateDto(GiftCertificateDto giftCertificateDto) {
        Link giftCertificateLink =
                linkTo(methodOn(GiftCertificateController.class)
                        .retrieveById(giftCertificateDto.getId())).withSelfRel();
        giftCertificateDto.add(giftCertificateLink);
    }

    private OrderDto addSelfLink(OrderDto orderDto, Long id) {
        Link selfLink = linkTo(OrderController.class).slash(id).withSelfRel();
        orderDto.add(selfLink);
        setLinkToGiftCertificateDto(orderDto.getGiftCertificateDto());
        addSelfLinkToTagDtoList(orderDto.getGiftCertificateDto().getTags());
        return orderDto;
    }

    private void addSelfLinkToTagDtoList(List<TagDto> tagDtoList) {
        tagDtoList.forEach(tagDto -> {
            Link selfLink = linkTo(methodOn(TagController.class).retrieveById(tagDto.getId())).withSelfRel();
            tagDto.add(selfLink);
        });
    }
}
