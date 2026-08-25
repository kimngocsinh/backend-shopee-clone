package com.shopee.backend.entity;

import com.shopee.backend.entity.Enum.NotificationType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 1000)
    private String content;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationType type = NotificationType.SYSTEM;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    /** Duong dan FE khi bam vao thong bao, vd /user/purchase/12. */
    @Column(length = 300)
    private String link;

    @Builder.Default
    @Column(name = "is_read")
    private Boolean isRead = false;
}
