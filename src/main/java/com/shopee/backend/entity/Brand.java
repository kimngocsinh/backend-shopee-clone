package com.shopee.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="brands")
public class Brand extends BaseEntity{
    @Column(nullable = false, length = 150)
    private String name;

    @Column(unique = true, length = 180)
    private String slug; /* samsung-electronics-viet-nam */

    @Column(name = "logo_url", length = 180)
    private String logoUrl;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = true;
}
