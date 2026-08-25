package com.shopee.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="shop_follows", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "shop_id"}))
public class ShopFollow extends BaseEntity{ // 1 user chi follow 1 shop 1 lan

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
