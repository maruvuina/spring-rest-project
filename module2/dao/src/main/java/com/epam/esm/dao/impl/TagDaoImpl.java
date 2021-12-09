package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_ID;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_NAME;
import static com.epam.esm.dao.util.SqlQuery.TAG_CREATE;
import static com.epam.esm.dao.util.SqlQuery.TAG_DELETE;
import static com.epam.esm.dao.util.SqlQuery.TAG_EXISTS;
import static com.epam.esm.dao.util.SqlQuery.TAG_FIND_ALL;
import static com.epam.esm.dao.util.SqlQuery.TAG_FIND_BY_GIFT_CERTIFICATE_ID;
import static com.epam.esm.dao.util.SqlQuery.TAG_FIND_BY_ID;
import static com.epam.esm.dao.util.SqlQuery.TAG_FIND_BY_NAME;

@Component
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Optional<Tag> create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(TAG_CREATE, new MapSqlParameterSource()
                .addValue(COLUMN_LABEL_NAME, tag.getName()), keyHolder, new String[]{COLUMN_LABEL_ID});
        return findById(keyHolder.getKey().longValue());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(TAG_DELETE, id);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(TAG_FIND_BY_ID,
                BeanPropertyRowMapper.newInstance(Tag.class), id));
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(TAG_FIND_ALL, BeanPropertyRowMapper.newInstance(Tag.class));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(TAG_FIND_BY_NAME,
                BeanPropertyRowMapper.newInstance(Tag.class), name));
    }

    @Override
    public List<Tag> findTagsByGiftCertificateId(Long giftCertificateId) {
        return jdbcTemplate.query(TAG_FIND_BY_GIFT_CERTIFICATE_ID, BeanPropertyRowMapper.newInstance(Tag.class), giftCertificateId);
    }

    @Override
    public boolean existsByName(String name) {
        return jdbcTemplate.queryForObject(TAG_EXISTS, Boolean.class, name);
    }
}
