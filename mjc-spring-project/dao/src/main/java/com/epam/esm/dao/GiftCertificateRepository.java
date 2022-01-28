package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>,
        JpaSpecificationExecutor<GiftCertificate> {

    /**
     * Exists by name.
     *
     * @param name the name
     * @return the boolean
     */
    boolean existsByName(String name);

    /**
     * Exists by name and not itself id.
     *
     * @param name the name
     * @param id   the id
     * @return the boolean
     */
    boolean existsByNameAndIdNot(String name, Long id);

    /**
     * Exists in order.
     *
     * @param id the id
     * @return the boolean
     */
    @Query("select case when count(o.giftCertificate.id) > 0 then true else false end " +
            "from Order o where o.giftCertificate.id = :id")
    boolean existsInOrder(@Param("id") Long id);
}
