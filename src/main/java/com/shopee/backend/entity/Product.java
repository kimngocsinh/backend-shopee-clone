package com.shopee.backend.entity;

import com.shopee.backend.entity.Enum.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="products")
public class Product extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(nullable = false, length = 300)
    private String name;

    @Column(length = 300)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    /** Gia ban hien tai (gia thap nhat trong cac phan loai). */
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    /** Gia goc truoc khi giam - dung de hien thi % giam gia. */
    @Column(name = "original_price", precision = 15, scale = 2)
    private BigDecimal originalPrice;

    @Builder.Default
    @Column(nullable = false)
    private Integer stock = 0;

    @Builder.Default
    private Integer sold = 0; // so luong da ban

    @Builder.Default
    private BigDecimal rating = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "rating_count")
    private Integer ratingCount = 0;

    @Builder.Default
    @Column(name = "view_count")
    private Integer viewCount = 0;

    @Builder.Default
    @Column(name = "favorite_count")
    private Integer favoriteCount = 0;

    /** NEW | USED - CONDITION la tu khoa cua MySQL nen phai doi ten cot. */
    @Builder.Default
    @Column(name = "product_condition", length = 20)
    private String condition = "NEW";

    /** Gram - dung tinh phi van chuyen. */
    @Builder.Default
    private Integer weight = 100;

    /** nguon goc xuat xu: viet nam, han quoc*/
    private String original;

    @Builder.Default
    @Column(name = "is_freeship")
    private Boolean isFreeship = false;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.ACTIVE;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<ProductImage> images = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<ProductOption> options = new ArrayList<>();

    public void addImage(ProductImage image) {
        image.setProduct(this);
        this.images.add(image);
    }

    public void addVariant(ProductVariant variant) {
        variant.setProduct(this);
        this.variants.add((variant));
    }

    public void addOption(ProductOption option) {
        option.setProduct(this);
        this.options.add(option);
    }


}
