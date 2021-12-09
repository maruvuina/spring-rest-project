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

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public TagDto create(@RequestBody TagDto tagDto) {
        return tagService.create(tagDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        tagService.delete(id);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<TagDto> retrieveAll() {
        return tagService.retrieveAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public TagDto retrieveById(@PathVariable("id") Long id) {
        return tagService.retrieveById(id);
    }
}
