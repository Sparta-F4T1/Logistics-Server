package com.logistic.auth.adapter.out.persistence;

import com.logistic.auth.adapter.out.persistence.repository.TokenRedisRepository;
import com.logistic.auth.application.port.out.persistence.AuthPersistencePort;
import com.logistic.auth.domain.User;
import com.logistic.auth.domain.vo.TokenCredential;
import com.logistic.auth.domain.vo.TokenId;
import com.logistic.auth.domain.vo.UserId;
import com.logistic.common.annotation.Adapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Adapter
@RequiredArgsConstructor
public class AuthPersistenceAdapter implements AuthPersistencePort {
  private final TokenRedisRepository tokenRedisRepository;

  @Override
  public User findByUserId(String userId) {
    return User.mock();
  }

  @Override
  public void saveRefreshToken(TokenId tokenId, UserId userId, TokenCredential refreshTokenCredential) {
    long ttlMillis = refreshTokenCredential.getTokenType().getExpiration().toMillis();
    String tokenValue = tokenId.value();
    String userIdValue = userId.value();
    tokenRedisRepository.saveRefreshToken(tokenValue, userIdValue, refreshTokenCredential.getTokenValue(),
        ttlMillis);
    log.info("리프레시 토큰 저장: userId={}, tokenId={}", userIdValue, tokenValue);
  }
}
