package com.epam.esm.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Represents a tag.
 */
@Where(clause = "is_deleted = false")
@Table(name = "tag")
@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(name = "is_deleted")
    private boolean isDeleted = Boolean.FALSE;
    @ManyToMany(mappedBy = "tags")
    private List<GiftCertificate> giftCertificates;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tag{");
        sb.append("idTag=").append(id);
        sb.append(", tagName='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
