package com.shopee.backend.mapper;

import com.shopee.backend.dto.response.ChatResponse;
import com.shopee.backend.dto.response.NotificationResponse;
import com.shopee.backend.dto.response.ReviewResponse;
import com.shopee.backend.dto.response.VoucherResponse;
import com.shopee.backend.entity.*;
import org.springframework.stereotype.Component;

@Component
public class MiscMapper {
    /* ---------------- Review ---------------- */

    public ReviewResponse toResponse(Review review) {
        if (review == null) {
            return null;
        }
        User user = review.getUser();
        boolean anonymous = Boolean.TRUE.equals(review.getIsAnonymous());
        String displayName = anonymous ? maskName(user.getUsername()) : user.getUsername();

        return ReviewResponse.builder()
                .id(review.getId())
                .productId(review.getProduct().getId())
                .productName(review.getProduct().getName())
                .productImage(review.getProduct().getThumbnailUrl())
                .userId(anonymous ? null : user.getId())
                .userName(displayName)
                .userAvatar(anonymous ? null : user.getAvatarUrl())
                .rating(review.getRating())
                .comment(review.getComment())
                .images(review.imageList())
                .variantName(review.getVariantName())
                .likeCount(review.getLikeCount())
                .sellerReply(review.getSellerReply())
                .sellerReplyAt(review.getSellerReplyAt())
                .createdAt(review.getCreatedAt())
                .build();
    }

    /** Che ten kieu Shopee: nguyenvana -> n*******a */
    private String maskName(String name) {
        if (name == null || name.length() <= 2) {
            return "*****";
        }
        return name.charAt(0) + "*".repeat(Math.max(1, name.length() - 2)) + name.charAt(name.length() - 1);
    }

    /* ---------------- Voucher ---------------- */

    public VoucherResponse toResponse(Voucher voucher) {
        if (voucher == null) {
            return null;
        }
        return VoucherResponse.builder()
                .id(voucher.getId())
                .code(voucher.getCode())
                .name(voucher.getName())
                .description(voucher.getDescription())
                .type(voucher.getType().name())
                .discountType(voucher.getDiscountType().name())
                .discountValue(voucher.getDiscountValue())
                .maxDiscount(voucher.getMaxDiscount())
                .minOrderAmount(voucher.getMinOrderAmount())
                .shopId(voucher.getShop() != null ? voucher.getShop().getId() : null)
                .shopName(voucher.getShop() != null ? voucher.getShop().getName() : null)
                .quantity(voucher.getQuantity())
                .usedCount(voucher.getUsedCount())
                .usageLimitPerUser(voucher.getUsageLimitPerUser())
                .startAt(voucher.getStartAt())
                .endAt(voucher.getEndAt())
                .isActive(voucher.getIsActive())
                .build();
    }

    /* ---------------- Notification ---------------- */

    public NotificationResponse toResponse(Notification notification) {
        if (notification == null) {
            return null;
        }
        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .type(notification.getType().name())
                .imageUrl(notification.getImageUrl())
                .link(notification.getLink())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    /* ---------------- Chat ---------------- */

    public ChatResponse.ConversationResponse toResponse(Conversation conversation, boolean asShop) {
        if (conversation == null) {
            return null;
        }
        return ChatResponse.ConversationResponse.builder()
                .id(conversation.getId())
                .shopId(conversation.getShop().getId())
                .shopName(conversation.getShop().getName())
                .shopLogo(conversation.getShop().getLogoUrl())
                .buyerId(conversation.getBuyer().getId())
                .buyerName(conversation.getBuyer().getUsername())
                .buyerAvatar(conversation.getBuyer().getAvatarUrl())
                .lastMessage(conversation.getLastMessage())
                .lastMessageAt(conversation.getLastMessageAt())
                .unreadCount(asShop ? conversation.getShopUnread() : conversation.getBuyerUnread())
                .build();
    }

    public ChatResponse.MessageResponse toResponse(Message message) {
        if (message == null) {
            return null;
        }
        ChatResponse.ProductRef productRef = null;
        if (message.getProduct() != null) {
            productRef = ChatResponse.ProductRef.builder()
                    .id(message.getProduct().getId())
                    .name(message.getProduct().getName())
                    .image(message.getProduct().getThumbnailUrl())
                    .price(message.getProduct().getPrice())
                    .build();
        }
        return ChatResponse.MessageResponse.builder()
                .id(message.getId())
                .conversationId(message.getConversation().getId())
                .senderId(message.getSender().getId())
                .senderName(message.getSender().getUsername())
                .senderAvatar(message.getSender().getAvatarUrl())
                .senderRole(message.getSenderRole())
                .content(message.getContent())
                .imageUrl(message.getImageUrl())
                .product(productRef)
                .isRead(message.getIsRead())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
