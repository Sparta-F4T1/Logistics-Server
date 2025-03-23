package com.logistic.auth.adapter.out.persistence;

import com.logistic.auth.adapter.out.persistence.mapper.AuthPersistenceMapper;
import com.logistic.auth.adapter.out.persistence.model.UserEntity;
import com.logistic.auth.adapter.out.persistence.repository.PassportRedisRepository;
import com.logistic.auth.adapter.out.persistence.repository.TokenRedisRepository;
import com.logistic.auth.adapter.out.persistence.repository.UserJpaRepository;
import com.logistic.auth.application.port.out.persistence.AuthPersistencePort;
import com.logistic.auth.domain.Passport;
import com.logistic.auth.domain.User;
import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;
import com.logistic.auth.domain.vo.TokenCredential;
import com.logistic.auth.domain.vo.TokenId;
import com.logistic.auth.domain.vo.UserId;
import com.logistic.common.annotation.Adapter;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Adapter
@RequiredArgsConstructor
public class AuthPersistenceAdapter implements AuthPersistencePort {
  private final TokenRedisRepository tokenRedisRepository;
  private final PassportRedisRepository passportRedisRepository;
  private final UserJpaRepository userJpaRepository;
  private final AuthPersistenceMapper mapper;

  @Override
  public void saveRefreshToken(TokenId tokenId, UserId userId, TokenCredential refreshTokenCredential) {
    long ttlMillis = refreshTokenCredential.getTokenType().getExpiration().toMillis();
    String tokenValue = tokenId.value();
    String userIdValue = userId.value();
    tokenRedisRepository.saveRefreshToken(tokenValue, userIdValue, refreshTokenCredential.getTokenValue(),
        ttlMillis);
    log.info("리프레시 토큰 저장: userId={}, tokenId={}", userIdValue, tokenValue);
  }

  @Override
  public boolean isValidRefreshToken(TokenId userId, String refreshToken) {
    if (userId == null || refreshToken == null || refreshToken.isEmpty()) {
      return false;
    }
    String storedToken = tokenRedisRepository.getRefreshToken(userId.value());

    return storedToken != null && storedToken.equals(refreshToken);
  }

  @Override
  public void addToBlacklist(TokenId tokenId, Instant expiration) {
    long ttlMillis = java.time.temporal.ChronoUnit.MILLIS.between(
        Instant.now(), expiration
    );
    if (ttlMillis <= 0) {
      return;
    }
    ttlMillis += 60_000;
    tokenRedisRepository.addToBlacklist(tokenId.value(), ttlMillis);
  }

  @Override
  public void removeRefreshToken(TokenId tokenId, UserId userId) {
    tokenRedisRepository.removeRefreshToken(tokenId.value(), userId.value());
  }

  @Override
  public boolean isBlacklisted(TokenId tokenId) {
    return tokenRedisRepository.isBlacklisted(tokenId.value());
  }

  @Override
  public User findByIdForLogin(String userId) {
    UserEntity userEntity = userJpaRepository.findByIdForLogin(userId)
        .orElseThrow(() -> AuthServiceException.auth(AuthServiceErrorCode.USER_NOT_FOUND));
    return mapper.toLoginDomain(userEntity);
  }

  @Override
  public User findByUserIdWithPermission(UserId userId) {
    return userJpaRepository.findByIdWithRoleAndPermissions(userId.value())
        .map(userEntity -> {
          User user = mapper.toDomain(userEntity);
          return user;
        })
        .orElseThrow(() -> AuthServiceException.auth(AuthServiceErrorCode.USER_NOT_FOUND));
  }

  @Override
  public Passport findPassportFromCache(UserId userId) {
    try {
      Passport passport = passportRedisRepository.findPassportByUserId(userId);
      return passport;
    } catch (AuthServiceException e) {
      throw e;
    }
  }

  @Override
  public void savePassport(Passport passport) {
    passportRedisRepository.savePassport(passport);
  }
}