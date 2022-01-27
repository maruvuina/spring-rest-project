package com.epam.esm.dao;

import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.util.Page;

import java.util.List;

public interface OrderDao extends CreateDao<Order>, GetDao<Order> {

    List<Order> retrieveByUserId(Long userId, Page page);
}
