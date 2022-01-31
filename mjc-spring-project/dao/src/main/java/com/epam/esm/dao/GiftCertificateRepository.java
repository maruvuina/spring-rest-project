package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    /**
     * Delete gift certificate.
     *
     * @param giftCertificate GiftCertificate
     */
    @Override
    @Query("update GiftCertificate g set g.isDeleted = true where g = :giftCertificate")
    @Modifying
    void delete(@NotNull @Param("giftCertificate") GiftCertificate giftCertificate);

    /**
     * Find all gift certificates.
     *
     * @param pageable Pageable
     * @return a page of giftCertificates
     */
    @NotNull
    @Override
    @Query("select g from GiftCertificate g where g.isDeleted = false")
    Page<GiftCertificate> findAll(@NotNull Pageable pageable);

    /**
     * Find gift certificate by id.
     *
     * @param id gift certificate id
     * @return the optional
     */
    @NotNull
    @Override
    @Query("select g from GiftCertificate g where g.id = :id and g.isDeleted = false")
    Optional<GiftCertificate> findById(@NotNull @Param("id") Long id);
}
