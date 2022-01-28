package com.epam.esm.dao.specification;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.GiftCertificate_;
import com.epam.esm.dao.entity.Tag_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.text.MessageFormat;
import java.util.List;

public final class GiftCertificateSpecification {

    private static final String PERCENT = "%";

    private GiftCertificateSpecification() {}

    public static Specification<GiftCertificate> nameContains(String name) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get(GiftCertificate_.NAME), contains(name));
    }

    public static Specification<GiftCertificate> descriptionContains(String description) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get(GiftCertificate_.DESCRIPTION), contains(description));
    }

    public static Specification<GiftCertificate> hasTags(List<String> tags) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.and(tags.stream()
                        .map(tagName -> criteriaBuilder.equal(root.join(GiftCertificate_.tags)
                                .get(Tag_.NAME), tagName))
                        .toArray(Predicate[]::new));
    }

    private static String contains(String expression) {
        return MessageFormat.format(PERCENT + "{0}" + PERCENT, expression);
    }
}
