package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.util.DynamicQuery;
import com.epam.esm.dao.util.GiftCertificateParameter;
import com.epam.esm.dao.util.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_ID;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_NAME;
import static com.epam.esm.dao.util.SqlQuery.GIFT_CERTIFICATE_EXISTS;
import static com.epam.esm.dao.util.SqlQuery.GIFT_CERTIFICATE_EXISTS_IN_ORDER;
import static com.epam.esm.dao.util.SqlQuery.GIFT_CERTIFICATE_EXISTS_UPDATE;
import static com.epam.esm.dao.util.SqlQuery.GIFT_CERTIFICATE_FIND_ALL;

@Component
@RequiredArgsConstructor
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        entityManager.remove(giftCertificate);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public List<GiftCertificate> findAll(Page page) {
        return entityManager.createQuery(GIFT_CERTIFICATE_FIND_ALL, GiftCertificate.class)
                .setFirstResult(page.getPageNumber() * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    @Override
    public List<GiftCertificate> findGiftCertificatesByParameter(Page page,
                                                                 GiftCertificateParameter giftCertificateParameter) {
        CriteriaQuery<GiftCertificate> giftCertificateCriteria =
                DynamicQuery.retrieveCriteria(giftCertificateParameter, entityManager.getCriteriaBuilder());
        return entityManager.createQuery(giftCertificateCriteria)
                .setFirstResult(page.getPageNumber() * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public boolean existsByName(String name) {
        return entityManager.createQuery(GIFT_CERTIFICATE_EXISTS, Boolean.class)
                .setParameter(COLUMN_LABEL_NAME, name).getSingleResult();
    }

    @Override
    public boolean existsByNameUpdate(String name, Long id) {
        return entityManager.createQuery(GIFT_CERTIFICATE_EXISTS_UPDATE, Boolean.class)
                .setParameter(COLUMN_LABEL_NAME, name)
                .setParameter(COLUMN_LABEL_ID, id)
                .getSingleResult();
    }

    @Override
    public boolean existsInOrder(Long id) {
        return entityManager.createQuery(GIFT_CERTIFICATE_EXISTS_IN_ORDER, Boolean.class)
                .setParameter(COLUMN_LABEL_ID, id).getSingleResult();
    }
}
