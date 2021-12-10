package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
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
import java.util.Objects;
import java.util.Optional;

import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_CREATE_DATE;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_DESCRIPTION;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_DURATION;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_ID;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_LAST_UPDATE_DATE;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_NAME;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_PRICE;
import static com.epam.esm.dao.util.SqlQuery.GIFT_CERTIFICATE_CREATE;
import static com.epam.esm.dao.util.SqlQuery.GIFT_CERTIFICATE_DELETE;
import static com.epam.esm.dao.util.SqlQuery.GIFT_CERTIFICATE_FIND_ALL;
import static com.epam.esm.dao.util.SqlQuery.GIFT_CERTIFICATE_FIND_BY_ID;
import static com.epam.esm.dao.util.SqlQuery.GIFT_CERTIFICATE_TAG_CREATE;
import static com.epam.esm.dao.util.SqlQuery.GIFT_CERTIFICATE_UPDATE;

@Component
@RequiredArgsConstructor
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Optional<GiftCertificate> create(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = getNamedParameters(giftCertificate);
        namedParameterJdbcTemplate.update(GIFT_CERTIFICATE_CREATE, namedParameters, keyHolder, new String[]{COLUMN_LABEL_ID});
        Optional<GiftCertificate> createdGiftCertificate = findById(keyHolder.getKey().longValue());
        createGiftCertificateTag(createdGiftCertificate.get().getId(), giftCertificate.getTags());
        return createdGiftCertificate;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(GIFT_CERTIFICATE_DELETE, id);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(GIFT_CERTIFICATE_FIND_BY_ID,
                BeanPropertyRowMapper.newInstance(GiftCertificate.class), id));
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(GIFT_CERTIFICATE_FIND_ALL,
                BeanPropertyRowMapper.newInstance(GiftCertificate.class));
    }

    @Override
    public List<GiftCertificate> findGiftCertificatesByParameter(String query, String parameter) {
        return !Objects.equals(parameter, "") ? jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(GiftCertificate.class), parameter) : retrieveSortedData(query);
    }

    @Override
    public Optional<GiftCertificate> update(Long id, GiftCertificate giftCertificate) {
        MapSqlParameterSource namedParameters = (MapSqlParameterSource) getNamedParameters(giftCertificate);
        namedParameters.addValue(COLUMN_LABEL_ID, id);
        namedParameterJdbcTemplate.update(GIFT_CERTIFICATE_UPDATE, namedParameters);
        return findById(id);
    }

    private Timestamp getDate(Instant instant) {
        return Timestamp.from(instant);
    }

    private SqlParameterSource getNamedParameters(GiftCertificate giftCertificate) {
        return new MapSqlParameterSource()
                .addValue(COLUMN_LABEL_NAME, giftCertificate.getName())
                .addValue(COLUMN_LABEL_DESCRIPTION, giftCertificate.getDescription())
                .addValue(COLUMN_LABEL_PRICE, giftCertificate.getPrice())
                .addValue(COLUMN_LABEL_DURATION, giftCertificate.getDuration())
                .addValue(COLUMN_LABEL_CREATE_DATE, getDate(giftCertificate.getCreateDate()))
                .addValue(COLUMN_LABEL_LAST_UPDATE_DATE, getDate(giftCertificate.getLastUpdateDate()));
    }

    private void createGiftCertificateTag(Long giftCertificateId, List<Tag> tags) {
        tags.forEach(tag -> jdbcTemplate.update(GIFT_CERTIFICATE_TAG_CREATE, giftCertificateId, tag.getId()));
    }

    private List<GiftCertificate> retrieveSortedData(String query) {
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(GiftCertificate.class));
    }
}
