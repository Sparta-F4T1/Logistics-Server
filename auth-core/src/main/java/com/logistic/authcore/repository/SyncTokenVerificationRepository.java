package com.logistic.authcore.repository;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * TokenVerificationRepository의 동기식 구현체 토큰 검증 및 조회 등 읽기 작업을 처리합니다.
 */
@Repository
@RequiredArgsConstructor
public class SyncTokenVerificationRepository implements TokenVerificationRepository {

  private final StringRedisTemplate redisTemplate;

  @Override
  public String hashToken(String token) {
    return DigestUtils.sha256Hex(token);
  }

  /**
   * 사용자 ID로 리프레시 토큰 키를 생성합니다.
   */
  protected String createRefreshTokenKey(String userId) {
    return REFRESH_TOKEN_PREFIX + userId;
  }

  /**
   * 사용자 ID로 액세스 토큰 키를 생성합니다.
   */
  protected String createAccessTokenKey(String userId) {
    return ACCESS_TOKEN_PREFIX + userId;
  }

  /**
   * 토큰 해시로 블랙리스트 키를 생성합니다.
   */
  protected String createBlacklistKey(String tokenHash) {
    return BLACKLIST_PREFIX + tokenHash;
  }

  @Override
  public boolean isTokenBlacklisted(String tokenHash) {
    String key = createBlacklistKey(tokenHash);
    return Boolean.TRUE.equals(redisTemplate.hasKey(key));
  }

  @Override
  public String getAccessToken(String userId) {
    String key = createAccessTokenKey(userId);
    return redisTemplate.opsForValue().get(key);
  }

  @Override
  public String getRefreshToken(String userId) {
    String key = createRefreshTokenKey(userId);
    return redisTemplate.opsForValue().get(key);
  }
}