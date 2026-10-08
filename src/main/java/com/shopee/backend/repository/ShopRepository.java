package com.shopee.backend.repository;

import com.shopee.backend.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findByOwnerId(Long ownerId); // tức là getUser.getId

    Optional<Shop> findBySlug(String slug);

    boolean existsByOwnerId(Long ownerId);

    boolean existsBySlug(String slug);
}
