package com.logistic.auth.adapter.out.support.jwt.config;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
  @Value("${jwt.secret}")
  private String secret;
  private Key key;

  @PostConstruct
  public void init() {
    try {
      byte[] bytes = Base64.getDecoder().decode(secret);
      key = Keys.hmacShaKeyFor(bytes);
    } catch (Exception e) {
      throw new RuntimeException("JWT 비밀키 초기화 실패", e);
    }
  }

  public Key getKey() {
    return this.key;
  }
}