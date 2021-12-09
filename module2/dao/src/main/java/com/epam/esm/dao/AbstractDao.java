package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T> {

    Optional<T> create(T t);

    void delete(Long id);

    Optional<T> findById(Long id);

    List<T> findAll();
}
