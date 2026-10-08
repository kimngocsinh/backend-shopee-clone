package com.shopee.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherResponse {

    private Long id;
    private String code;
    private String name;
    private String description;
    private String type;
    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal maxDiscount;
    private BigDecimal minOrderAmount;
    private Long shopId;
    private String shopName;
    private Integer quantity;
    private Integer usedCount;
    private Integer usageLimitPerUser;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Boolean isActive;
    private Boolean isSaved;
    /** Tinh cho gio hang hien tai: co dung duoc khong. */
    private Boolean usable;
    private String unusableReason;
    private BigDecimal estimatedDiscount;
}
