package com.epam.esm.dao;

import com.epam.esm.dao.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends AbstractDao<Tag> {

    Optional<Tag> findByName(String name);

    List<Tag> findTagsByGiftCertificateId(Long giftCertificateId);

    boolean existsByName(String name);
}
