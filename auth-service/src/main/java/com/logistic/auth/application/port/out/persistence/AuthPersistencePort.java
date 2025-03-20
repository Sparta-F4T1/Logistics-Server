package com.logistic.auth.application.port.out.persistence;

import com.logistic.auth.domain.User;
import com.logistic.auth.domain.vo.TokenCredential;
import com.logistic.auth.domain.vo.TokenId;
import com.logistic.auth.domain.vo.UserId;
import java.time.Instant;

public interface AuthPersistencePort {
  User findByUserId(String username);

  void saveRefreshToken(TokenId tokenId, UserId userId, TokenCredential refreshTokenCredential);

  boolean isValidRefreshToken(TokenId oldTokenId, String refreshToken);

  void addToBlacklist(TokenId oldTokenId, Instant expirationTime);

  void removeRefreshToken(TokenId oldTokenId, UserId userId);

  boolean isBlacklisted(TokenId tokenId);
}