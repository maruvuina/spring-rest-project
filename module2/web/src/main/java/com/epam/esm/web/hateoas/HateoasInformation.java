package com.epam.esm.web.hateoas;

import com.epam.esm.service.dto.GiftCertificateDto;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HateoasInformation {

    public CollectionModel<TagDto> addSelfLinkToTagList(List<TagDto> tags) {
        addSelfLinkToTagDtoList(tags);
        Link link = linkTo(TagController.class).withSelfRel();
        return CollectionModel.of(tags, link);
    }

    public TagDto addSelfLinkToTag(TagDto tag, Long id) {
        Link selfLink = linkTo(TagController.class).slash(id).withSelfRel();
        tag.add(selfLink);
        return tag;
    }

    public TagDto addSelfLinkToTagUsers(TagDto tag, Long id) {
        Link selfLink = linkTo(TagController.class).slash("users").slash(id).withSelfRel();
        tag.add(selfLink);
        return tag;
    }

    public CollectionModel<GiftCertificateDto> addSelfLinkToGiftCertificateList(List<GiftCertificateDto>
                                                                                        giftCertificates) {
        giftCertificates.forEach(giftCertificate -> {
            Link selfLink = linkTo(methodOn(GiftCertificateController.class)
                    .retrieveById(giftCertificate.getId())).withSelfRel();
            giftCertificate.add(selfLink);
            addSelfLinkToTagDtoList(giftCertificate.getTags());
        });
        Link link = linkTo(GiftCertificateController.class).withSelfRel();
        return CollectionModel.of(giftCertificates, link);
    }

    public GiftCertificateDto addSelfLinkToGiftCertificate(GiftCertificateDto giftCertificate, Long id) {
        Link selfLink = linkTo(GiftCertificateController.class).slash(id).withSelfRel();
        giftCertificate.add(selfLink);
        addSelfLinkToTagDtoList(giftCertificate.getTags());
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
        Link selfLink = linkTo(OrderController.class).slash(id).withSelfRel();
        order.add(selfLink);
        setLinkToGiftCertificateDto(order.getGiftCertificate());
        addSelfLinkToTagDtoList(order.getGiftCertificate().getTags());
        return order;
    }

    private void addSelfLinkToTagDtoList(List<TagDto> tags) {
        tags.forEach(tag -> {
            Link selfLink = linkTo(methodOn(TagController.class).retrieveById(tag.getId())).withSelfRel();
            tag.add(selfLink);
        });
    }

    private void setLinkToGiftCertificateDto(GiftCertificateDto giftCertificate) {
        Link giftCertificateLink =
                linkTo(methodOn(GiftCertificateController.class)
                        .retrieveById(giftCertificate.getId())).withSelfRel();
        giftCertificate.add(giftCertificateLink);
    }
}
