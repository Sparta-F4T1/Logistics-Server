package com.logistic.auth.adapter.out.persistence.repository;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenRedisRepository {
  private static final String REFRESH_TOKEN_PREFIX = "refresh:token:";
  private static final String BLACKLIST_PREFIX = "blacklist:";
  private static final String USER_TOKENS_PREFIX = "user:tokens:";

  private final StringRedisTemplate redisTemplate;

  public void saveRefreshToken(String tokenId, String userId, String refreshToken, long ttlMillis) {
    String tokenKey = REFRESH_TOKEN_PREFIX + tokenId;
    redisTemplate.opsForValue().set(tokenKey, refreshToken, ttlMillis, TimeUnit.MILLISECONDS);

    // 사용자 ID와 토큰 ID 매핑 정보 저장 (Set 자료구조 사용), ttl 적용 -> TODO: 확장기능
    String userTokensKey = USER_TOKENS_PREFIX + userId;
    redisTemplate.opsForSet().add(userTokensKey, tokenId);
    redisTemplate.expire(userTokensKey, ttlMillis, TimeUnit.MILLISECONDS);
  }

  public void addToBlacklist(String tokenId, long ttlMillis) {
    String key = BLACKLIST_PREFIX + tokenId;
    redisTemplate.opsForValue().set(key, "1", ttlMillis, TimeUnit.MILLISECONDS);
  }

  public void removeRefreshToken(String tokenId, String userId) {
    String refreshTokenKey = REFRESH_TOKEN_PREFIX + tokenId;

    Boolean exists = redisTemplate.hasKey(refreshTokenKey);
    if (Boolean.TRUE.equals(exists)) {
      redisTemplate.delete(refreshTokenKey);
    }
    redisTemplate.opsForSet().remove(USER_TOKENS_PREFIX + userId, tokenId);
  }

  public boolean isBlacklisted(String tokenId) {
    String key = BLACKLIST_PREFIX + tokenId;
    return Boolean.TRUE.equals(redisTemplate.hasKey(key));
  }

  public String getRefreshToken(String tokenId) {
    String key = REFRESH_TOKEN_PREFIX + tokenId;
    return redisTemplate.opsForValue().get(key);
  }
}