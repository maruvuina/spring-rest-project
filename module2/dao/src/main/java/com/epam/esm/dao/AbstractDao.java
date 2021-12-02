package com.epam.esm.dao;

import java.util.List;

public abstract class AbstractDao<T> {

    public abstract T create(T t);

    public abstract void update(T entity);

    public abstract void delete(int id);

    public abstract T findById(int id);

    public abstract List<T> findAll();
}
