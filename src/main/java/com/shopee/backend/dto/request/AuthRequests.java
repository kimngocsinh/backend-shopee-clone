package com.shopee.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class AuthRequests {

    @Data
    public static class RegisterRequest {
        @NotBlank(message = "Ten dang nhap khong duoc de trong")
        @Size(min = 4, max = 50, message = "Ten dang nhap tu 4 den 50 ky tu")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Ten dang nhap chi gom chu, so, dau , _ -")
        private String username;

        @NotBlank(message = "Email khong duoc de trong")
        @Email(message = "Email khong hop le")
        private String email;

        @NotBlank(message = "So dien thoai khong duoc de trong")
        @Pattern(regexp = "^[035789]\\d{8}$", message = "So dien thoai khong hop le")
        private String phone;

        @NotBlank(message = "Mat khau khong duoc de trong")
        @Size(min = 6, max = 100, message = "Mat khau toi thieu 6 ky tu")
        private String password;

        private String fullName;
    }

    @Data
    public static class LoginRequest {
        @NotBlank(message = "Vui long nha ten dang nhap")
        private String login;

        @NotBlank(message = "Vui long nhap mat khau")
        private String password;
    }
}
