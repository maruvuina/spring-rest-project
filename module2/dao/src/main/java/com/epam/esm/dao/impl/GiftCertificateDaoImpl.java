package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.util.DynamicQueryResult;
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
    public Optional<GiftCertificate> create(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return Optional.of(giftCertificate);
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
    public List<GiftCertificate> findAll(Integer page, Integer size) {
        return entityManager.createQuery(GIFT_CERTIFICATE_FIND_ALL, GiftCertificate.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<GiftCertificate> findGiftCertificatesByParameter(Integer page, Integer size, DynamicQueryResult dynamicQueryResult) {
        Query managerQuery = entityManager.createQuery(dynamicQueryResult.getQuery(), GiftCertificate.class);
        setParameters(managerQuery, dynamicQueryResult.getParameters());
        return managerQuery
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    private void setParameters(Query query, Map<String, String> parameters) {
        parameters.forEach(query::setParameter);
    }
}
