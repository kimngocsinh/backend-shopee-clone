package com.shopee.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="banners")
public class Banner extends BaseEntity{
    @Column(length = 200)
    private String title;

    @Column(name = "image_url", nullable = false, length = 150)
    private String imageUrl;

    @Column(name = "link_url", length = 500)
    private String linkUrl;

    /* HOME_SLIDER | HOME_SIDE | CATEGORY_TOP */
    @Builder.Default
    @Column(nullable = false, length = 30)
    private String position = "HOME_SLIDER";

    @Builder.Default
    @Column(name = "display_order")
    private Integer displayOrder = 0; /* thứ tự hiển thị*/

    @Builder.Default
    @Column(name = "is_active")
    private boolean isActive = true;
}
