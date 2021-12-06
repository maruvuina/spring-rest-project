package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends AbstractDao<Tag> {
    Optional<Tag> findByName(String name);

    Optional<List<Tag>> findTagsByGiftCertificateId(Integer giftCertificateId);
}
