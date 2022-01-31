package com.epam.esm.dao;

import com.epam.esm.dao.entity.Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Order repository.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find by user id.
     *
     * @param userId   the user id
     * @param pageable the pageable
     * @return the page
     */
    @Query("select o from Order o where o.user.id = :userId and o.isDeleted = false")
    Page<Order> findByUserId(@Param("userId") Long userId, Pageable pageable);

    /**
     * Find all orders.
     *
     * @param pageable Pageable
     * @return a page of orders
     */
    @NotNull
    @Override
    @Query("select o from Order o where o.isDeleted = false")
    Page<Order> findAll(@NotNull Pageable pageable);

    /**
     * Find order by id.
     *
     * @param id order id
     * @return the optional
     */
    @NotNull
    @Override
    @Query("select o from Order o where o.id = :id and o.isDeleted = false")
    Optional<Order> findById(@NotNull @Param("id") Long id);
}
