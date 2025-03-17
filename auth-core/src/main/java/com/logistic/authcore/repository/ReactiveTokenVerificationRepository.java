package com.logistic.authcore.repository;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ReactiveTokenVerificationRepository implements TokenVerificationRepository {

  private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

  @Override
  public String hashToken(String token) {
    return DigestUtils.sha256Hex(token);
  }

  protected String createRefreshTokenKey(String userId) {
    return REFRESH_TOKEN_PREFIX + userId;
  }

  protected String createAccessTokenKey(String userId) {
    return ACCESS_TOKEN_PREFIX + userId;
  }

  protected String createBlacklistKey(String tokenHash) {
    return BLACKLIST_PREFIX + tokenHash;
  }

  @Override
  public boolean isTokenBlacklisted(String tokenHash) {
    String key = createBlacklistKey(tokenHash);
    return Boolean.TRUE.equals(reactiveRedisTemplate.hasKey(key).block());
  }

  @Override
  public String getAccessToken(String userId) {
    String key = createAccessTokenKey(userId);
    return reactiveRedisTemplate.opsForValue().get(key).block();
  }

  @Override
  public String getRefreshToken(String userId) {
    String key = createRefreshTokenKey(userId);
    return reactiveRedisTemplate.opsForValue().get(key).block();
  }

  public Mono<Boolean> isTokenBlacklistedReactive(String tokenHash) {
    String key = createBlacklistKey(tokenHash);
    return reactiveRedisTemplate.hasKey(key);
  }

  public Mono<String> getAccessTokenReactive(String userId) {
    String key = createAccessTokenKey(userId);
    return reactiveRedisTemplate.opsForValue().get(key);
  }

  public Mono<String> getRefreshTokenReactive(String userId) {
    String key = createRefreshTokenKey(userId);
    return reactiveRedisTemplate.opsForValue().get(key);
  }
}