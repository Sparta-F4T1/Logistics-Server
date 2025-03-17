package com.logistic.authcore.repository;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SyncAuthTokenRepository implements AuthTokenRepository {

  private final StringRedisTemplate redisTemplate;

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
  public void saveRefreshToken(String userId, String refreshToken, Duration duration) {
    String key = createRefreshTokenKey(userId);
    redisTemplate.opsForValue().set(key, refreshToken, duration.toMillis(), TimeUnit.MILLISECONDS);
  }

  @Override
  public void saveAccessToken(String userId, String accessToken, Duration duration) {
    String key = createAccessTokenKey(userId);
    redisTemplate.opsForValue().set(key, accessToken, duration.toMillis(), TimeUnit.MILLISECONDS);
  }

  @Override
  public void blacklistToken(String tokenHash, Duration duration) {
    String key = createBlacklistKey(tokenHash);
    redisTemplate.opsForValue().set(key, "1", duration.toMillis(), TimeUnit.MILLISECONDS);
  }

  @Override
  public boolean deleteAccessToken(String userId) {
    String key = createAccessTokenKey(userId);
    return Boolean.TRUE.equals(redisTemplate.delete(key));
  }

  @Override
  public boolean deleteRefreshToken(String userId) {
    String key = createRefreshTokenKey(userId);
    return Boolean.TRUE.equals(redisTemplate.delete(key));
  }
}