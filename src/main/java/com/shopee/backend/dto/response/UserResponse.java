package com.shopee.backend.dto.response;

import com.shopee.backend.entity.Enum.Gender;
import com.shopee.backend.entity.Enum.UserStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String phone;
    private String fullName;
    private String avatarUrl;
    private Gender gender;
    private LocalDate dateOfBirth;
    private UserStatus status;
    private Boolean emailVerified;
    private BigDecimal walletBalance;
    private Integer shopeeCoin;
    private Set<String> roles;
    private Long shopId;
    private LocalDateTime createdAt;
}
