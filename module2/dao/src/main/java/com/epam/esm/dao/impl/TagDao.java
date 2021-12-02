package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.epam.esm.dao.SqlQuery.*;

@Component
public class TagDao extends AbstractDao<Tag> {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TagDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(TAG_CREATE, new MapSqlParameterSource()
                .addValue("name", tag.getName()), keyHolder, new String[]{"id"});
        return findById(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }

    @Override
    public void update(Tag entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(TAG_DELETE, id);
    }

    @Override
    public Tag findById(int id) {
        return jdbcTemplate.queryForObject(TAG_FIND_BY_ID, new BeanPropertyRowMapper<>(Tag.class), id);
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(TAG_FIND_ALL, new BeanPropertyRowMapper<>(Tag.class));
    }

    public Tag findByName(String name) {
        return jdbcTemplate.queryForObject(TAG_FIND_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), name);
    }
}
