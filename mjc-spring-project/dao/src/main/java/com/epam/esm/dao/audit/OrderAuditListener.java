package com.epam.esm.dao.audit;

import com.epam.esm.dao.entity.Order;

import javax.persistence.PrePersist;
import java.time.Instant;

public class OrderAuditListener {

    @PrePersist
    private void setLastUpdateDate(Order order) {
        order.setPurchaseDate(Instant.now());
    }
}
