package com.logistic.authcore.util;

import com.logistic.authcore.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

  public static final String CLAIM_USER_ID = "userId";
  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
  private final JwtProperties jwtProperties;

  private String secret;
  private Key key;

  @PostConstruct
  public void init() {
    this.secret = jwtProperties.getSecret();
    try {
      byte[] bytes = Base64.getDecoder().decode(secret);
      key = Keys.hmacShaKeyFor(bytes);
      log.debug("JWT 키 초기화 성공: 비밀키 길이 {} 바이트", secret.length());
    } catch (Exception e) {
      log.error("JWT 키 초기화 실패: {}", e.getMessage());
      throw new RuntimeException("JWT 비밀키 초기화 실패", e);
    }
  }

  public String createAccessToken(@NonNull String userId) {
    Date now = new Date();
    Date expiryDate = Date.from(Instant.now().plus(jwtProperties.getAccessTokenDuration()));

    return Jwts.builder()
        .setSubject(userId)
        .claim(CLAIM_USER_ID, userId)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(key, signatureAlgorithm)
        .compact();
  }

  public String createRefreshToken(@NonNull String userId) {
    Date now = new Date();
    Date expiryDate = Date.from(Instant.now().plus(jwtProperties.getRefreshTokenDuration()));

    return Jwts.builder()
        .setSubject(userId)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(key)
        .compact();
  }

  public Claims validateToken(@NonNull String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public long getTokenRemainingTimeMs(@NonNull String token) {
    Claims claims = validateToken(token);
    Date expiration = claims.getExpiration();
    return Math.max(0, expiration.getTime() - System.currentTimeMillis());
  }
}