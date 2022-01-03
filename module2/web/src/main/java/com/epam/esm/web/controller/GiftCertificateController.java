package com.epam.esm.web.controller;

import com.epam.esm.dao.util.Page;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.dao.util.GiftCertificateParameter;
import com.epam.esm.service.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
 * Gift certificate controller.
 */
@RestController
@RequestMapping("/v1/certificates")
@RequiredArgsConstructor
@Validated
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    /**
     * Create gift certificate.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto createdGiftCertificateDto = giftCertificateService.create(giftCertificateDto);
        return addSelfLink(createdGiftCertificateDto, createdGiftCertificateDto.getId());
    }

    /**
     * Delete gift certificate.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") @Min(1) @Max(Long.MAX_VALUE) Long id) {
        giftCertificateService.delete(id);
    }

    /**
     * Get gift certificate by id.
     *
     * @param id the id
     * @return the by id
     */
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public GiftCertificateDto retrieveById(@PathVariable("id") @Min(1) @Max(Long.MAX_VALUE) Long id) {
        return addSelfLink(giftCertificateService.retrieveById(id), id);
    }

    /**
     * Update gift certificate.
     *
     * @param id                 the id
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public GiftCertificateDto update(@PathVariable("id") @Min(1) @Max(Long.MAX_VALUE) Long id,
                                     @RequestBody GiftCertificateDto giftCertificateDto) {
        return addSelfLink(giftCertificateService.update(id, giftCertificateDto), id);
    }

    /**
     * Update part gift certificate.
     *
     * @param id                 the id
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    @PatchMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public GiftCertificateDto updatePart(@PathVariable("id") @Min(1) @Max(Long.MAX_VALUE) Long id,
                                         @RequestBody GiftCertificateDto giftCertificateDto) {
        return addSelfLink(giftCertificateService.updatePart(id, giftCertificateDto), id);
    }

    /**
     * Retrieve all tags and by parameter.
     *
     * @param pageNumber               the page number
     * @param size                     the size
     * @param giftCertificateParameter the gift certificate parameter
     * @return the collection model of gift certificate dto
     */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public CollectionModel<GiftCertificateDto> retrieveAll(@RequestParam(defaultValue = "0", name = "page")
                                                @Min(0) @Max(Integer.MAX_VALUE) Integer pageNumber,
                                                @RequestParam(defaultValue = "3")
                                                @Min(1) @Max(Integer.MAX_VALUE) Integer size,
                                                GiftCertificateParameter giftCertificateParameter) {
        return addSelfLinkToList(giftCertificateService.retrieveGiftCertificatesByParameter(new Page(pageNumber, size),
                giftCertificateParameter));
    }

    private CollectionModel<GiftCertificateDto> addSelfLinkToList(List<GiftCertificateDto> giftCertificateDtoList) {
        giftCertificateDtoList.forEach(giftCertificateDto -> {
            Link selfLink = linkTo(methodOn(GiftCertificateController.class)
                    .retrieveById(giftCertificateDto.getId())).withSelfRel();
            giftCertificateDto.add(selfLink);
            addSelfLinkToTagDtoList(giftCertificateDto.getTags());
        });
        Link link = linkTo(GiftCertificateController.class).withSelfRel();
        return CollectionModel.of(giftCertificateDtoList, link);
    }

    private GiftCertificateDto addSelfLink(GiftCertificateDto giftCertificateDto, Long id) {
        Link selfLink = linkTo(GiftCertificateController.class).slash(id).withSelfRel();
        giftCertificateDto.add(selfLink);
        addSelfLinkToTagDtoList(giftCertificateDto.getTags());
        return giftCertificateDto;
    }

    private void addSelfLinkToTagDtoList(List<TagDto> tagDtoList) {
        tagDtoList.forEach(tagDto -> {
            Link selfLink = linkTo(methodOn(TagController.class).retrieveById(tagDto.getId())).withSelfRel();
            tagDto.add(selfLink);
        });
    }
}
