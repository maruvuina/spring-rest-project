package com.epam.esm.dao.entity;

import com.epam.esm.dao.audit.OrderAuditListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Table(name = "order_table")
@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners({OrderAuditListener.class})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_gift_certificate", nullable = false)
    private GiftCertificate giftCertificate;
    @Column(name = "purchase_date", nullable = false)
    private Instant purchaseDate;
    @Column(nullable = false)
    private BigDecimal cost;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", giftCertificate=").append(giftCertificate);
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append(", cost=").append(cost);
        sb.append('}');
        return sb.toString();
    }
}
