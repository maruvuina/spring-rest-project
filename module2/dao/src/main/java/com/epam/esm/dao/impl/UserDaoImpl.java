package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.util.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_ID;
import static com.epam.esm.dao.util.SqlQuery.HAS_USER_ORDERS;
import static com.epam.esm.dao.util.SqlQuery.USER_EXISTS;
import static com.epam.esm.dao.util.SqlQuery.USER_FIND_ALL;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll(Page page) {
        return entityManager.createQuery(USER_FIND_ALL, User.class)
                .setFirstResult(page.getPageNumber() * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public boolean hasUserOrders(Long id) {
        return entityManager.createQuery(HAS_USER_ORDERS, Boolean.class)
                .setParameter(COLUMN_LABEL_ID, id)
                .getSingleResult();
    }

    @Override
    public boolean existsById(Long id) {
        return entityManager.createQuery(USER_EXISTS, Boolean.class)
                .setParameter(COLUMN_LABEL_ID, id)
                .getSingleResult();
    }
}
