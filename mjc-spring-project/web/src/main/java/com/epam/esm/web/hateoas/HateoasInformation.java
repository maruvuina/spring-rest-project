package com.epam.esm.web.hateoas;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderCreateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.web.controller.GiftCertificateController;
import com.epam.esm.web.controller.OrderController;
import com.epam.esm.web.controller.TagController;
import com.epam.esm.web.controller.UserController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HateoasInformation {

    private static final String CREATE = "create";
    private static final String UPDATE = "update";
    private static final String GET_BY_ID = "get by id";
    private static final String USERS = "users";

    public CollectionModel<TagDto> addSelfLinkToTagList(List<TagDto> tags) {
        addSelfLinkToTagDtoList(tags);
        Link link = linkTo(TagController.class).withSelfRel();
        return CollectionModel.of(tags, link);
    }

    public TagDto addSelfLinkToTag(TagDto tag, Long id) {
        tag.add(linkTo(TagController.class).slash(id).withSelfRel());
        tag.add(linkTo(methodOn(TagController.class).create(tag)).withRel(CREATE));
        return tag;
    }

    public TagDto addSelfLinkToTagUsers(TagDto tag, Long id) {
        tag.add(linkTo(TagController.class).slash(USERS).slash(id).withSelfRel());
        return tag;
    }

    public CollectionModel<GiftCertificateDto> addSelfLinkToGiftCertificateList(List<GiftCertificateDto>
                                                                                        giftCertificates) {
        List<GiftCertificateDto> giftCertificateDtoList = giftCertificates.stream()
                .map(giftCertificate -> {
                    giftCertificate = addSelfLinkToGiftCertificate(giftCertificate, giftCertificate.getId());
                    addSelfLinkToTagDtoList(giftCertificate.getTags());
                    return giftCertificate;
                })
                .collect(Collectors.toList());
        Link link = linkTo(GiftCertificateController.class).withSelfRel();
        return CollectionModel.of(giftCertificateDtoList, link);
    }

    public GiftCertificateDto addSelfLinkToGiftCertificate(GiftCertificateDto giftCertificate, Long id) {
        giftCertificate.add(linkTo(GiftCertificateController.class).slash(id).withSelfRel());
        giftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
                .create(giftCertificate)).withRel(CREATE));
        giftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
                .update(id, giftCertificate)).withRel(UPDATE));
        return giftCertificate;
    }

    public CollectionModel<UserDto> addSelfLinkToUserList(List<UserDto> users) {
        users.forEach(user -> {
            Link selfLink = linkTo(methodOn(UserController.class).retrieveById(user.getId())).withSelfRel();
            user.add(selfLink);
        });
        Link link = linkTo(TagController.class).withSelfRel();
        return CollectionModel.of(users, link);
    }

    public UserDto addSelfLinkToUser(UserDto user, Long id) {
        Link selfLink = linkTo(UserController.class).slash(id).withSelfRel();
        user.add(selfLink);
        return user;
    }

    public CollectionModel<OrderDto> addSelfLinkToOrderList(List<OrderDto> orders) {
        orders.forEach(order -> {
            Link selfLink = linkTo(methodOn(OrderController.class)
                    .retrieveById(order.getId())).withSelfRel();
            order.add(selfLink);
            setLinkToGiftCertificateDto(order.getGiftCertificate());
            addSelfLinkToTagDtoList(order.getGiftCertificate().getTags());
        });
        Link link = linkTo(OrderController.class).withSelfRel();
        return CollectionModel.of(orders, link);
    }

    public OrderDto addSelfLinkToOrder(OrderDto order, Long id) {
        order.add(linkTo(OrderController.class).slash(id).withSelfRel());
        order.add(linkTo(methodOn(OrderController.class).create(new OrderCreateDto())).withRel(CREATE));
        setLinkToGiftCertificateDto(order.getGiftCertificate());
        addSelfLinkToTagDtoList(order.getGiftCertificate().getTags());
        return order;
    }

    private void addSelfLinkToTagDtoList(List<TagDto> tags) {
        tags.forEach(tag -> {
            tag.add(linkTo(methodOn(TagController.class).retrieveById(tag.getId())).withRel(GET_BY_ID));
            tag.add(linkTo(methodOn(TagController.class).create(tag)).withRel(CREATE));
        });
    }

    private void setLinkToGiftCertificateDto(GiftCertificateDto giftCertificate) {
        Link giftCertificateLink =
                linkTo(methodOn(GiftCertificateController.class)
                        .retrieveById(giftCertificate.getId())).withSelfRel();
        giftCertificate.add(giftCertificateLink);
    }
}
