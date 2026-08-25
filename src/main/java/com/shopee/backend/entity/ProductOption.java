package com.shopee.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="product_options")
public class ProductOption extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "option_value", nullable = false, length = 1000)
    private String values;

    @Builder.Default
    private Integer position = 0; // vị trí thứ tự hiển thi

    public List<String> valueList() {
        if (!StringUtils.hasText(values)) {
            return List.of();
        }
        return Arrays.stream(values.split("\\|"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

}
