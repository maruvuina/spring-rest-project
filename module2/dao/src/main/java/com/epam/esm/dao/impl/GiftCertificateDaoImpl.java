package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.entity.GiftCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_CREATE_DATE;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_DESCRIPTION;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_DURATION;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_ID;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_LAST_UPDATE_DATE;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_NAME;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_PRICE;
import static com.epam.esm.dao.util.SqlQuery.*;
import static com.epam.esm.dao.util.SqlQuery.GIFT_CERTIFICATE_FIND_BY_NAME;

@Component
@RequiredArgsConstructor
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Optional<GiftCertificate> create(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue(COLUMN_LABEL_NAME, giftCertificate.getName())
                .addValue(COLUMN_LABEL_DESCRIPTION, giftCertificate.getDescription())
                .addValue(COLUMN_LABEL_PRICE, giftCertificate.getPrice())
                .addValue(COLUMN_LABEL_DURATION, giftCertificate.getDuration())
                .addValue(COLUMN_LABEL_CREATE_DATE, getDate(giftCertificate.getCreateDate()))
                .addValue(COLUMN_LABEL_LAST_UPDATE_DATE, getDate(giftCertificate.getCreateDate()));
        namedParameterJdbcTemplate.update(GIFT_CERTIFICATE_CREATE, namedParameters, keyHolder, new String[]{COLUMN_LABEL_ID});
        return findById(keyHolder.getKey().intValue());
    }

    private Timestamp getDate(Instant instant) {
        return Timestamp.from(instant);
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(GIFT_CERTIFICATE_DELETE, id);
    }

    @Override
    public Optional<GiftCertificate> findById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(GIFT_CERTIFICATE_FIND_BY_ID,
                BeanPropertyRowMapper.newInstance(GiftCertificate.class), id));
    }

    @Override
    public Optional<List<GiftCertificate>> findAll() {
        return Optional.of(jdbcTemplate.query(GIFT_CERTIFICATE_FIND_ALL,
                BeanPropertyRowMapper.newInstance(GiftCertificate.class)));
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificate giftCertificate) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue(COLUMN_LABEL_NAME, giftCertificate.getName())
                .addValue(COLUMN_LABEL_DESCRIPTION, giftCertificate.getDescription())
                .addValue(COLUMN_LABEL_PRICE, giftCertificate.getPrice())
                .addValue(COLUMN_LABEL_DURATION, giftCertificate.getDuration())
                .addValue(COLUMN_LABEL_CREATE_DATE, getDate(giftCertificate.getCreateDate()))
                .addValue(COLUMN_LABEL_LAST_UPDATE_DATE, getDate(giftCertificate.getCreateDate()))
                .addValue(COLUMN_LABEL_ID, giftCertificate.getId());
        namedParameterJdbcTemplate.update(GIFT_CERTIFICATE_UPDATE, namedParameters);
        return Optional.empty();
    }

    @Override
    public void createGiftCertificateTag(int giftCertificateId, int tagId) {
        jdbcTemplate.update(GIFT_CERTIFICATE_TAG_CREATE, giftCertificateId, tagId);
    }

    @Override
    public Optional<List<GiftCertificate>> findSortedGiftCertificates(String query) {
        return Optional.of(jdbcTemplate.query(query,
                BeanPropertyRowMapper.newInstance(GiftCertificate.class)));
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(GIFT_CERTIFICATE_FIND_BY_NAME,
                BeanPropertyRowMapper.newInstance(GiftCertificate.class), name));
    }

    @Override
    public Optional<List<GiftCertificate>> findByTagName(String tagName) {
        return Optional.of(jdbcTemplate.query(GIFT_CERTIFICATE_FIND_BY_TAG_NAME,
                BeanPropertyRowMapper.newInstance(GiftCertificate.class), tagName));
    }

    @Override
    public Optional<List<GiftCertificate>> findByPartOfName(String partOfName) {
        return Optional.of(jdbcTemplate.query(GIFT_CERTIFICATE_SEARCH_BY_PART_NAME,
                BeanPropertyRowMapper.newInstance(GiftCertificate.class), partOfName + "%"));
    }

    @Override
    public Optional<List<GiftCertificate>> findByPartOfDescription(String partOfDescription) {
        return Optional.of(jdbcTemplate.query(GIFT_CERTIFICATE_SEARCH_BY_PART_DESCRIPTION,
                BeanPropertyRowMapper.newInstance(GiftCertificate.class), partOfDescription + "%"));
    }
}
