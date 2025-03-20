package com.logistic.auth.adapter.out.persistence.repository;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenRedisRepository {
  private static final String REFRESH_TOKEN_PREFIX = "refresh:token:";   // 토큰 ID로 리프레시 토큰 저장
  private static final String BLACKLIST_PREFIX = "blacklist:";           // 블랙리스트
  private static final String USER_TOKENS_PREFIX = "user:tokens:";       // 사용자 ID와 토큰 ID 매핑

  private final StringRedisTemplate redisTemplate;

  public void saveRefreshToken(String tokenId, String userId, String refreshToken, long ttlMillis) {
    String tokenKey = REFRESH_TOKEN_PREFIX + tokenId;
    redisTemplate.opsForValue().set(tokenKey, refreshToken, ttlMillis, TimeUnit.MILLISECONDS);

    // 사용자 ID와 토큰 ID 매핑 정보 저장 (Set 자료구조 사용), ttl 적용 -> TODO: 확장기능
    String userTokensKey = USER_TOKENS_PREFIX + userId;
    redisTemplate.opsForSet().add(userTokensKey, tokenId);
    redisTemplate.expire(userTokensKey, ttlMillis, TimeUnit.MILLISECONDS);
  }
}