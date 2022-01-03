package com.epam.esm.web.controller;

import com.epam.esm.dao.util.Page;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * User controller.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    /**
     * Retrieve all users.
     *
     * @param pageNumber the page number
     * @param size the size
     * @return the collection model of user dto
     */
    @GetMapping
    public CollectionModel<UserDto> retrieveAll(@RequestParam(defaultValue = "0", name = "page")
                                     @Min(0) @Max(Integer.MAX_VALUE) Integer pageNumber,
                                     @RequestParam(defaultValue = "3")
                                     @Min(1) @Max(Integer.MAX_VALUE) Integer size) {
        return addSelfLinkToList(userService.retrieveAll(new Page(pageNumber, size)));
    }

    /**
     * Retrieve user by id.
     *
     * @param id the id
     * @return the user dto
     */
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserDto retrieveById(@PathVariable("id") @Min(1) @Max(Long.MAX_VALUE) Long id) {
        return addSelfLink(userService.retrieveById(id), id);
    }

    private CollectionModel<UserDto> addSelfLinkToList(List<UserDto> userDtoList) {
        userDtoList.forEach(userDto -> {
            Link selfLink = linkTo(methodOn(UserController.class).retrieveById(userDto.getId())).withSelfRel();
            userDto.add(selfLink);
        });
        Link link = linkTo(TagController.class).withSelfRel();
        return CollectionModel.of(userDtoList, link);
    }

    private UserDto addSelfLink(UserDto userDto, Long id) {
        Link selfLink = linkTo(UserController.class).slash(id).withSelfRel();
        userDto.add(selfLink);
        return userDto;
    }
}
