package com.shopee.backend.entity;

import com.shopee.backend.entity.Enum.DiscountType;
import com.shopee.backend.entity.Enum.VoucherType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="vouchers")
public class Voucher extends BaseEntity{

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VoucherType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false, length = 20)
    private DiscountType discountType;

    /** Neu PERCENT thi la % (vd 10), neu FIXED_AMOUNT thi la so tien. */
    @Column(name = "discount_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal discountValue;

    @Builder.Default
    @Column(name = "max_discount", precision = 15, scale = 2)
    private BigDecimal maxDiscount = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "min_order_amount", precision = 15, scale = 2)
    private BigDecimal minOrderAmount = BigDecimal.ZERO;

    /** null = voucher toan san. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Builder.Default
    private Integer quantity = 100;

    @Builder.Default
    @Column(name = "used_count")
    private Integer usedCount = 0;

    @Builder.Default
    @Column(name = "usage_limit_per_user")
    private Integer usageLimitPerUser = 1;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = true;

    public boolean isAvailable() {
        LocalDateTime now = LocalDateTime.now();
        return Boolean.TRUE.equals(isActive)
                && now.isAfter(startAt) && now.isBefore(endAt)
                && usedCount < quantity;
    }
}
