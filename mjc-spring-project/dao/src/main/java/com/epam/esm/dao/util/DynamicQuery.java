package com.epam.esm.dao.util;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.GiftCertificate_;
import com.epam.esm.dao.entity.Tag_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class DynamicQuery {

    private static final String PERCENT = "%";

    private DynamicQuery() {}

    public static CriteriaQuery<GiftCertificate> retrieveCriteria(GiftCertificateParameter giftCertificateParameter,
                          CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<GiftCertificate> giftCertificateCriteria = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateRoot = giftCertificateCriteria.from(GiftCertificate.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.addAll(retrieveByName(giftCertificateParameter, criteriaBuilder, giftCertificateRoot));
        predicates.addAll(retrieveByDescription(giftCertificateParameter, criteriaBuilder, giftCertificateRoot));
        predicates.addAll(retrieveByTagName(giftCertificateParameter, criteriaBuilder, giftCertificateRoot));
        giftCertificateCriteria.select(giftCertificateRoot)
                .where(predicates.toArray(new Predicate[]{}));
        setSortAndOrder(giftCertificateParameter, criteriaBuilder, giftCertificateCriteria, giftCertificateRoot);
        return giftCertificateCriteria;
    }

    private static void setSortAndOrder(GiftCertificateParameter giftCertificateParameter,
                         CriteriaBuilder criteriaBuilder,
                         CriteriaQuery<GiftCertificate> giftCertificateCriteria,
                         Root<GiftCertificate> giftCertificateRoot) {
        if (giftCertificateParameter.getSort() != null) {
            giftCertificateCriteria
                    .orderBy(OrderType.DESC.equals(giftCertificateParameter.getOrder()) ?
                            criteriaBuilder.desc(giftCertificateRoot
                                    .get(giftCertificateParameter.getSort().getValue())) :
                            criteriaBuilder.asc(giftCertificateRoot
                                    .get(giftCertificateParameter.getSort().getValue())));
        }
    }

    private static List<Predicate> retrieveByName(GiftCertificateParameter giftCertificateParameter,
                                            CriteriaBuilder criteriaBuilder,
                                            Root<GiftCertificate> giftCertificateRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (giftCertificateParameter.getName() != null) {
            predicates.add(criteriaBuilder.like(giftCertificateRoot.get(GiftCertificate_.NAME),
                    PERCENT + giftCertificateParameter.getName() + PERCENT));
        }
        return predicates;
    }

    private static List<Predicate> retrieveByDescription(GiftCertificateParameter giftCertificateParameter,
                                                   CriteriaBuilder criteriaBuilder,
                                                   Root<GiftCertificate> giftCertificateRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (giftCertificateParameter.getDescription() != null) {
            predicates.add(criteriaBuilder.like(giftCertificateRoot.get(GiftCertificate_.DESCRIPTION),
                    PERCENT + giftCertificateParameter.getDescription() + PERCENT));
        }
        return predicates;
    }

    private static List<Predicate> retrieveByTagName(GiftCertificateParameter giftCertificateParameter,
                                                     CriteriaBuilder criteriaBuilder,
                                               Root<GiftCertificate> giftCertificateRoot) {
        List<Predicate> predicates = new ArrayList<>();
        List<String> tags = giftCertificateParameter.getTagName();
        if (tags != null) {
            predicates.addAll(tags.stream()
                    .map(tagName -> criteriaBuilder.equal(giftCertificateRoot
                                    .join(GiftCertificate_.tags)
                                    .get(Tag_.NAME), tagName))
                    .collect(Collectors.toList()));
        }
        return predicates;
    }
}