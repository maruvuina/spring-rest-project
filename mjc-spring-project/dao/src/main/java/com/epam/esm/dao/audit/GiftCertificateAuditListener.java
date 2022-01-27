package com.epam.esm.dao.audit;

import com.epam.esm.dao.entity.GiftCertificate;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

public class GiftCertificateAuditListener {

    @PrePersist
    private void setDateOnCreate(GiftCertificate giftCertificate) {
        giftCertificate.setCreateDate(retrieveDate());
        giftCertificate.setLastUpdateDate(retrieveDate());
    }

    @PreUpdate
    private void setLastUpdateDate(GiftCertificate giftCertificate) {
        giftCertificate.setLastUpdateDate(retrieveDate());
    }

    private Instant retrieveDate() {
        return Instant.now();
    }
}
