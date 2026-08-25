package com.shopee.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Column(nullable = false)
    private Integer rating;

    @Column(length = 2000)
    private String comment;

    /** Danh sach URL anh, ngan cach boi dau |. */
    @Column(name = "image_urls", length = 2000)
    private String imageUrls;

    @Column(name = "variant_name", length = 200)
    private String variantName;

    @Builder.Default
    @Column(name = "is_anonymous")
    private Boolean isAnonymous = false;

    @Builder.Default
    @Column(name = "like_count")
    private Integer likeCount = 0;

    @Column(name = "seller_reply", length = 1000)
    private String sellerReply;

    @Column(name = "seller_reply_at")
    private LocalDateTime sellerReplyAt;

    public List<String> imageList() {
        if (imageUrls == null || imageUrls.isBlank()) {
            return List.of();
        }
        return Arrays.stream(imageUrls.split("\\|")).filter(s -> !s.isBlank()).toList();
    }
}
