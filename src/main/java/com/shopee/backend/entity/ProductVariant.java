package com.shopee.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Mot to hop phan loai cu the, vi du: "Do - Size L".
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="product_variants")
public class ProductVariant extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    /** ten hien thi, VD "Do, L" */
    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 100)
    private String sku;

    @Column(name = "option1_value", length = 100)
    private String option1Value;

    @Column(name = "option2_value", length = 100)
    private String option2Value;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "original_price", precision = 15, scale = 2)
    private BigDecimal originalPrice;

    @Builder.Default
    @Column(nullable = false)
    private Integer stock = 0;

    @Builder.Default
    private Integer sold = 0;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

}
