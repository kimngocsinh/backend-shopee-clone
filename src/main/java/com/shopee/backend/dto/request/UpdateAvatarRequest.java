package com.shopee.backend.dto.request;

import jakarta.validation.constraints.NotBlank;

// day là cách viết mới của java 16 trở đi cũng tương tự như các dto khác
public record UpdateAvatarRequest(
        @NotBlank(message = "URL avatar khong duoc de trong")
        String avatarUrl
) {}
