package com.shopee.backend.entity;

import com.shopee.backend.entity.Enum.OrderStatus;
import com.shopee.backend.entity.Enum.PaymentMethod;
import com.shopee.backend.entity.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    @Column(nullable = false, unique = true, length = 30)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderStatusHistory> histories = new ArrayList<>();

    /* ---------- Tien ---------- */
    @Column(name = "subtotal", nullable = false, precision = 15, scale = 2)
    private BigDecimal subtotal;

    @Builder.Default
    @Column(name = "shipping_fee", precision = 15, scale = 2)
    private BigDecimal shippingFee = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "shop_discount", precision = 15, scale = 2)
    private BigDecimal shopDiscount = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "platform_discount", precision = 15, scale = 2)
    private BigDecimal platformDiscount = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "shipping_discount", precision = 15, scale = 2)
    private BigDecimal shippingDiscount = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "coin_discount", precision = 15, scale = 2)
    private BigDecimal coinDiscount = BigDecimal.ZERO;

    /**
     * So xu se duoc thuong khi don hoan thanh. Chi cong vao vi khi don sang COMPLETED,
     * neu cong ngay luc dat hang thi nguoi mua co the dat roi huy lien tuc de farm xu.
     */
    @Builder.Default
    @Column(name = "coin_earned")
    private Integer coinEarned = 0;

    /** Danh dau da cong xu thuong roi, tranh cong hai lan. */
    @Builder.Default
    @Column(name = "coin_credited")
    private Boolean coinCredited = false;

    /** Danh dau da hoan lai xu da dung, tranh hoan hai lan. */
    @Builder.Default
    @Column(name = "coin_refunded")
    private Boolean coinRefunded = false;

    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    /* ---------- Voucher ---------- */
    @Column(name = "shop_voucher_code", length = 50)
    private String shopVoucherCode;

    @Column(name = "platform_voucher_code", length = 50)
    private String platformVoucherCode;

    @Column(name = "shipping_voucher_code", length = 50)
    private String shippingVoucherCode;

    /* ---------- Nguoi nhan (snapshot) ---------- */
    @Column(name = "receiver_name", nullable = false, length = 150)
    private String receiverName;

    @Column(name = "receiver_phone", nullable = false, length = 20)
    private String receiverPhone;

    @Column(name = "receiver_address", nullable = false, length = 500)
    private String receiverAddress;

    /* ---------- Trang thai ---------- */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrderStatus status = OrderStatus.PENDING;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 30)
    private PaymentMethod paymentMethod = PaymentMethod.COD;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @Column(name = "shipping_method", length = 50)
    private String shippingMethod;

    @Column(name = "tracking_number", length = 60)
    private String trackingNumber;

    @Column(length = 500)
    private String note;

    @Column(name = "cancel_reason", length = 500)
    private String cancelReason;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "shipped_at")
    private LocalDateTime shippedAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "expected_delivery")
    private LocalDateTime expectedDelivery;

    @Builder.Default
    @Column(name = "is_reviewed")
    private Boolean isReviewed = false;

    public void addItem(OrderItem item) {
        item.setOrder(this);
        this.items.add(item);
    }

    public void addHistory(OrderStatusHistory history) {
        history.setOrder(this);
        this.histories.add(history);
    }
}
