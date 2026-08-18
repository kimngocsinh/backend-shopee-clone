package com.shopee.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="categories")
public class Category extends BaseEntity{
    @Column(nullable = false, length = 150)
    private String name;

    @Column(unique = true, length = 180)
    private String slug;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<Category> children = new ArrayList<>();

    @Builder.Default
    private Integer level = 1;

    @Builder.Default
    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = true;
}
