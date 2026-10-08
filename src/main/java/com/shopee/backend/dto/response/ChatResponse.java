package com.shopee.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ChatResponse {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConversationResponse {
        private Long id;
        private Long shopId;
        private String shopName;
        private String shopLogo;
        private Long buyerId;
        private String buyerName;
        private String buyerAvatar;
        private String lastMessage;
        private LocalDateTime lastMessageAt;
        private Integer unreadCount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageResponse {
        private Long id;
        private Long conversationId;
        private Long senderId;
        private String senderName;
        private String senderAvatar;
        private String senderRole;
        private String content;
        private String imageUrl;
        private ProductRef product;
        private Boolean isRead;
        private LocalDateTime createdAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductRef {
        private Long id;
        private String name;
        private String image;
        private BigDecimal price;
    }
}
