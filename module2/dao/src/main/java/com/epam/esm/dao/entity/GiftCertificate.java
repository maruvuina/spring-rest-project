package com.epam.esm.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * Represents a Gift certificate.
 */
@Table(name = "gift_certificate")
@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "duration", nullable = false)
    private Integer duration;
    @Column(name = "create_date", nullable = false)
    private Instant createDate;
    @Column(name = "last_update_date", nullable = false)
    private Instant lastUpdateDate;
    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY)
    @JoinTable(name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;
    @OneToMany(mappedBy = "giftCertificate")
    private List<Order> orders;

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getGiftCertificates().add(this);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getGiftCertificates().remove(this);
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setGiftCertificate(this);
    }

    public void removeOrder(Order order) {
        this.orders.remove(order);
        order.setGiftCertificate(null);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GiftCertificate{");
        sb.append("idGiftCertificate=").append(id);
        sb.append(", giftCertificateName='").append(name).append('\'');
        sb.append(", description=").append(description);
        sb.append(", price=").append(price);
        sb.append(", duration='").append(duration).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", lastUpdateDate=").append(lastUpdateDate);
        sb.append('}');
        return sb.toString();
    }
}
