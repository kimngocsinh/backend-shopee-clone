package com.shopee.backend.security;

import com.shopee.backend.entity.Enum.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtService {

    private final SecretKey accessKey;
    private final SecretKey refreshKey;
    @Getter
    private final long accessTokenExpiration;
    @Getter
    private final long refreshTokenExpiration;

    public JwtService(
            @Value("${app.jwt.access-secret-key}") String accessSecretKey,
            @Value("${app.jwt.refresh-secret-key}") String refreshSecretKey,
            @Value("${app.jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${app.jwt.refresh-token-expiration}") long refreshTokenExpiration
    ) {
        this.accessKey = buildSecretKey(accessSecretKey);
        this.refreshKey = buildSecretKey(refreshSecretKey);
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    private SecretKey getKey(TokenType type) {
        return switch (type) {
            case ACCESS_TOKEN -> accessKey;
            case REFRESH_TOKEN -> refreshKey;
        };
    }

    private SecretKey buildSecretKey(String secretKey) {
        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(secretKey);
        } catch (Exception e) {
            keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UserPrincipal userPrincipal) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", userPrincipal.getId());
        claims.put("email", userPrincipal.getEmail());
        claims.put("roles", userPrincipal.getAuthorities().stream().map(Object::toString).toList());
        return buildToken(claims, userPrincipal.getUsername(), accessTokenExpiration, TokenType.ACCESS_TOKEN);
    }

    public String generateRefreshToken(UserPrincipal userPrincipal) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", userPrincipal.getId());
        claims.put("email", userPrincipal.getEmail());
        claims.put("roles", userPrincipal.getAuthorities().stream().map(Object::toString).toList());
        return buildToken(claims, userPrincipal.getUsername(), refreshTokenExpiration, TokenType.REFRESH_TOKEN);
    }

    public String extractUsername(String token) {
        return getClaims(token, TokenType.ACCESS_TOKEN).getSubject();
    }

    public Claims getClaims(String token, TokenType type) {
        return Jwts.parser().verifyWith(getKey(type)).build().parseSignedClaims(token).getPayload();
    }

    public boolean isTokenValid(String token, UserPrincipal principal) {
        Claims claims = getClaims(token, TokenType.ACCESS_TOKEN);
        return claims.getSubject().equals(principal.getUsername())
                && claims.getExpiration().after(new Date());
    }


    private String buildToken(Map<String, Object> claims, String subject, long expiration, TokenType tokenType) {
        Date now = new Date();
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .signWith(getKey(tokenType))
                .compact();
    }

}
