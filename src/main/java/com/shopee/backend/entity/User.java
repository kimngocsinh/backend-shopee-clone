package com.shopee.backend.entity;

import com.shopee.backend.entity.Enum.Gender;
import com.shopee.backend.entity.Enum.RoleName;
import com.shopee.backend.entity.Enum.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User extends BaseEntity {
    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(unique = true, length = 20)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name", length = 150)
    private String fullName;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus userStatus = UserStatus.ACTIVE;

    @Builder.Default
    @Column(name = "email_verified")
    private Boolean emailVerified = false;

    @Builder.Default
    @Column(name = "phone_verified")
    private Boolean phoneVerified = false;

    /* so du shopeePay (mo phong) */
    @Builder.Default
    @Column(name = "wallet_balance", precision = 15, scale = 2)
    private BigDecimal walletBalance = BigDecimal.ZERO;

    @Column(name = "shopee_coin")
    private Integer shopeeCoin = 0;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 30)
    private Set<RoleName> roles = new HashSet<>();

    public boolean hasRole(RoleName roleName) {
        return roleName != null && roles.contains(roleName);
    }
}
