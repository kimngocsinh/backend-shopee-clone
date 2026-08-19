package com.shopee.backend.dto.request;

import com.shopee.backend.entity.Enum.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

public class UserRequests {

    @Data
    public static class UpdateProfileRequest {
        @Size(max = 150)
        private String fullName;

        @Email(message = "Email khong hop le")
        private String email;

        @NotBlank(message = "So dien thoai khong duoc de trong")
        @Pattern(regexp = "^0[35789]\\d{8}$", message = "So dien thoai khong hop le")
        private String phone;

        private Gender gender;

        private LocalDate dateOfBirth;

        private String avatarUrl;
    }

    @Data
    public static class AddressRequest {
        @NotBlank(message = "Ho ten nguoi nhan khong duoc de trong")
        private String fullName;

        @NotBlank(message = "So dien thoai khong duoc de trong")
        @Pattern(regexp = "^0[35789]\\d{8}$", message = "So dien thoai khong hop le")
        private String phone;

        @NotBlank(message = "Vui long chon Tinh/Thanh pho")
        private String province;

        @NotBlank(message = "Vui long chon Quan/Huyen")
        private String district;

        @NotBlank(message = "Vui long chon Xa/Phuong")
        private String ward;

        @NotBlank(message = "Vui long nhap dia chi cu the")
        private String detailAddress;

        private String type; /*HOME | OFFICE */

        private Boolean isDefault;
    }
}
