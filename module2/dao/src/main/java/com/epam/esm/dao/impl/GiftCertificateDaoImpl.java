package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.util.DynamicQueryResult;
import com.epam.esm.dao.util.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                .setFirstResult(page.getPage() * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    @Override
    public List<GiftCertificate> findGiftCertificatesByParameter(Page page, DynamicQueryResult dynamicQueryResult) {
        Query query = entityManager.createQuery(dynamicQueryResult.getQuery(), GiftCertificate.class);
        setParameters(query, dynamicQueryResult.getParameters());
        return query.setFirstResult(page.getPage() * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    private void setParameters(Query query, Map<String, String> parameters) {
        parameters.forEach(query::setParameter);
    }
}
