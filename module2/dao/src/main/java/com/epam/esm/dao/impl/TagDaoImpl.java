package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.util.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_ID;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_NAME;
import static com.epam.esm.dao.util.SqlQuery.TAG_EXISTS;
import static com.epam.esm.dao.util.SqlQuery.TAG_EXISTS_IN_GIFT_CERTIFICATE_TAG;
import static com.epam.esm.dao.util.SqlQuery.TAG_FIND_ALL;
import static com.epam.esm.dao.util.SqlQuery.TAG_FIND_BY_GIFT_CERTIFICATE_ID;
import static com.epam.esm.dao.util.SqlQuery.TAG_FIND_BY_NAME;

@Component
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public List<Tag> findAll(Page page) {
        return entityManager.createQuery(TAG_FIND_ALL, Tag.class)
                .setFirstResult(page.getPageNumber() * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return Optional.of((Tag) entityManager.createQuery(TAG_FIND_BY_NAME)
                .setParameter(COLUMN_LABEL_NAME, name).getSingleResult());
    }

    @Override
    public List<Tag> findTagsByGiftCertificateId(Long giftCertificateId) {
        return entityManager.createQuery(TAG_FIND_BY_GIFT_CERTIFICATE_ID, Tag.class)
                .setParameter(COLUMN_LABEL_ID, giftCertificateId).getResultList();
    }

    @Override
    public boolean existsByName(String name) {
        return entityManager.createQuery(TAG_EXISTS, Boolean.class)
                .setParameter(COLUMN_LABEL_NAME, name).getSingleResult();
    }

    @Override
    public boolean existsInGiftCertificateTag(Long tagId) {
        return (boolean) entityManager.createNativeQuery(TAG_EXISTS_IN_GIFT_CERTIFICATE_TAG)
                .setParameter(COLUMN_LABEL_ID, tagId).getSingleResult();
    }
}
