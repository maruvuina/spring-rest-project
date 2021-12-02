package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import static com.epam.esm.dao.SqlQuery.*;
import static com.epam.esm.dao.SqlQuery.GIFT_CERTIFICATE_FIND_BY_NAME;

@Component
public class GiftCertificateDao extends AbstractDao<GiftCertificate> {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public GiftCertificateDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", giftCertificate.getName())
                .addValue("description", giftCertificate.getDescription())
                .addValue("price", giftCertificate.getPrice())
                .addValue("duration", giftCertificate.getDuration())
                .addValue("createDate", new Timestamp(giftCertificate.getCreateDate().getTime()))
                .addValue("lastUpdateDate", new Timestamp(giftCertificate.getCreateDate().getTime()));
        namedParameterJdbcTemplate.update(GIFT_CERTIFICATE_CREATE, namedParameters, keyHolder, new String[]{"id"});
        return findById(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }

    @Override
    public void update(GiftCertificate entity) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("updatedName", entity.getName())
                .addValue("updatedDescription", entity.getDescription())
                .addValue("id", entity.getId());
        namedParameterJdbcTemplate.update(GIFT_CERTIFICATE_UPDATE, namedParameters);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(GIFT_CERTIFICATE_DELETE, id);
    }

    @Override
    public GiftCertificate findById(int id) {
        return jdbcTemplate.queryForObject(GIFT_CERTIFICATE_FIND_BY_ID, new BeanPropertyRowMapper<>(GiftCertificate.class), id);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(GIFT_CERTIFICATE_FIND_ALL, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    public void createGiftCertificateTag(int giftCertificateId, int tagId) {
        jdbcTemplate
                .update("INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (?, ?)", giftCertificateId, tagId);
    }

    public GiftCertificate findByName(String name) {
        return jdbcTemplate.queryForObject(GIFT_CERTIFICATE_FIND_BY_NAME, new BeanPropertyRowMapper<>(GiftCertificate.class), name);
    }
}
