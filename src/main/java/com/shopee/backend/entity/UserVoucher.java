package com.shopee.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user_vouchers", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "voucher_id"}))
public class UserVoucher extends BaseEntity{ /** Voucher da duoc user "luu" vao kho voucher cua minh. */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @Builder.Default
    @Column(name = "used_count")
    private Integer usedCount = 0;

    @Column(name = "last_used_at")
    private LocalDateTime lastUsedAt;
}
