package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.util.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.dao.util.SqlQuery.ORDER_FIND_ALL;

@Component
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order create(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public List<Order> findAll(Page page) {
        return entityManager.createQuery(ORDER_FIND_ALL, Order.class)
                .setFirstResult(page.getPage() * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }
}
