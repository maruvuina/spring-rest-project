package com.epam.esm.web.controller;

import com.epam.esm.dao.util.Page;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
 * Tag controller.
 */
@RestController
@RequestMapping("/v1/tags")
@RequiredArgsConstructor
@Validated
public class TagController {

    private final TagService tagService;

    /**
     * Create tag.
     *
     * @param tagDto the tag dto
     * @return the tag dto
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TagDto create(@Valid @RequestBody TagDto tagDto) {
        return tagService.create(tagDto);
    }

    /**
     * Delete tag by id.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") @Min(1) @Max(Long.MAX_VALUE) Long id) {
        tagService.delete(id);
    }


    /**
     * Retrieve all tags.
     *
     * @param pageNumber the page number
     * @param size the size
     * @return the list of tag dto
     */
    @GetMapping
    public List<TagDto> retrieveAll(@RequestParam(defaultValue = "0", name = "page")
                                    @Min(0) @Max(Integer.MAX_VALUE) Integer pageNumber,
                                    @RequestParam(defaultValue = "3")
                                    @Min(1) @Max(Integer.MAX_VALUE) Integer size) {
        return tagService.retrieveAll(new Page(pageNumber, size));
    }

    /**
     * Retrieve tag by id.
     *
     * @param id the id
     * @return the tag dto
     */
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public TagDto retrieveById(@PathVariable("id") @Min(1) @Max(Long.MAX_VALUE) Long id) {
        return tagService.retrieveById(id);
    }

    /**
     * Retrieve most popular user tag by user id.
     *
     * @param id the user id
     * @return the tag dto
     */
    @GetMapping("/users/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public TagDto retrieveMostPopularUserTagByUserId(@PathVariable("id") @Min(1) @Max(Long.MAX_VALUE) Long id) {
        return tagService.retrieveMostPopularUserTagByUserId(id);
    }
}
