package com.shopee.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="product_variants")
public class Product extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(nullable = false, length = 300)
    private String name;

    @Column(length = 300)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;
}
