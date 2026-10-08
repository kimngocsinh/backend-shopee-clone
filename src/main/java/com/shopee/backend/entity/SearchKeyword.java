package com.shopee.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Tu khoa tim kiem pho bien - dung cho goi y o thanh search. */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "search_keywords")
public class SearchKeyword extends BaseEntity {

    @Column(nullable = false, unique = true, length = 200)
    private String keyword;

    @Builder.Default
    @Column(name = "search_count")
    private Long searchCount = 1L;
}
