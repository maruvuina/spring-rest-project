package com.epam.esm.dao;

import com.epam.esm.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Check if user has orders.
     *
     * @param id the id
     * @return the boolean
     */
    @Query("select case when count(o) > 0 then true else false end from Order o where o.user.id = :id")
    boolean hasUserOrders(@Param("id") Long id);

    /**
     * Find by email.
     *
     * @param email the email
     * @return the optional
     */
    Optional<User> findByEmail(String email);

    /**
     * Exists by email.
     *
     * @param email the email
     * @return the boolean
     */
    boolean existsByEmail(String email);
}
