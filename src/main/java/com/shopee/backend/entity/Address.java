package com.shopee.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name ="addresses")
public class Address extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(nullable = false, length = 30)
    private String phone;

    @Column(nullable = false, length = 100)
    private String province;

    @Column(nullable = false, length = 100)
    private String district;

    @Column(nullable = false, length = 100)
    private String ward;

    @Column(name = "detail_address", nullable = false, length = 300)
    private String detailAddress;

    @Builder.Default
    @Column(length = 20)
    private String addressType = "HOME";

    @Builder.Default
    @Column(name = "is_default")
    private boolean isDefault = false;

    public String toFullAddress() {
        return String.join(", ", detailAddress, ward, district, province);
    }
}
