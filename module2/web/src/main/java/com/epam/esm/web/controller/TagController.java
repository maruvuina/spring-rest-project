package com.epam.esm.web.controller;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Tag controller.
 */
@RestController
@RequestMapping("/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * Create tag.
     *
     * @param tagDto the tag dto
     * @return the tag dto
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public TagDto create(@RequestBody TagDto tagDto) {
        return tagService.create(tagDto);
    }

    /**
     * Delete tag by id.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        tagService.delete(id);
    }

    /**
     * Retrieve all tags.
     *
     * @return the list
     */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<TagDto> retrieveAll() {
        return tagService.retrieveAll();
    }

    /**
     * Retrieve tag by id tag.
     *
     * @param id the id
     * @return the tag dto
     */
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public TagDto retrieveById(@PathVariable("id") Long id) {
        return tagService.retrieveById(id);
    }
}
