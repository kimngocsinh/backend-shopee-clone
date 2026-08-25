package com.shopee.backend.entity.Enum;

/**
 * Trang thai don hang, map 1-1 voi cac tab trong "Don mua" cua Shopee.
 */
public enum OrderStatus {
    PENDING("Cho xac nhan"),
    CONFIRMED("Cho lay hang"),
    SHIPPING("Cho giao hang"),
    DELIVERED("Da giao"),
    COMPLETED("Hoan thanh"),
    CANCELLED("Da huy"),
    RETURN_REQUESTED("Yeu cau tra hang"),
    RETURNED("Tra hang/Hoan tien");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

