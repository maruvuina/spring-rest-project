package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T> {

    Optional<T> create(T t);

    void delete(Integer id);

    Optional<T> findById(Integer id);

    Optional<List<T>> findAll();
}
