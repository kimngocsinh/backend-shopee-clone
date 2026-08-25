package com.shopee.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id")
    private User sender;

    /** BUYER | SHOP */
    @Column(name = "sender_role", nullable = false, length = 10)
    private String senderRole;

    @Column(length = 2000)
    private String content;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    /** Gan kem san pham dang hoi (giong Shopee chat). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder.Default
    @Column(name = "is_read")
    private Boolean isRead = false;
}
