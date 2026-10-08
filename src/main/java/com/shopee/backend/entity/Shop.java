package com.shopee.backend.entity;

import com.shopee.backend.entity.Enum.ShopStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="shops")
public class Shop extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(unique = true, length = 180)
    private String slug;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(name = "cover_url", length = 500)
    private String coverUrl;

    @Column(length = 1000)
    private String description;

    @Column(length = 300)
    private String address;

    @Column(length = 20)
    private String phone;

    @Builder.Default
    @Column(precision = 3, scale = 2)
    private BigDecimal rating = BigDecimal.valueOf(5); // Danh gia ban dau la 5 sao

    @Builder.Default
    @Column(name = "rating_count")
    private Integer ratingCount = 0;

    @Builder.Default
    @Column(name = "follower_count")
    private Integer followerCount = 0;

    @Builder.Default
    @Column(name = "product_count")
    private Integer productCount = 0;

    /** Ti le phan hoi chat (%) - hien thi tren trang shop. */
    @Builder.Default
    @Column(name = "response_rate")
    private Integer responseRate = 95;

    /* xac nhan la mall hay k*/
    @Builder.Default
    @Column(name = "is_official")
    private Boolean isOfficial = false;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ShopStatus status = ShopStatus.ACTIVE;
}
