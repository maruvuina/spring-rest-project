package com.epam.esm.service;

import java.util.List;

public interface AbstractService<T> {

    T create(T t);

    void delete(Long id);

    List<T> retrieveAll();

    T retrieveById(Long id);
}
