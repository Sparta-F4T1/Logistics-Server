package com.logistic.auth.application.port.out.persistence;

import com.logistic.auth.domain.User;
import com.logistic.auth.domain.vo.TokenCredential;
import com.logistic.auth.domain.vo.TokenId;
import com.logistic.auth.domain.vo.UserId;

public interface AuthPersistencePort {
  User findByUserId(String username);

  void saveRefreshToken(TokenId tokenId, UserId userId, TokenCredential refreshTokenCredential);
}