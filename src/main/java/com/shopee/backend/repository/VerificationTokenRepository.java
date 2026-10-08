package com.shopee.backend.repository;

import com.shopee.backend.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByTokenAndPurposeAndUsedFalse(String token, String purpose);

    Optional<VerificationToken> findFirstByUserIdAndPurposeAndUsedFalseOrderByIdDesc(Long userId, String purpose);
}

