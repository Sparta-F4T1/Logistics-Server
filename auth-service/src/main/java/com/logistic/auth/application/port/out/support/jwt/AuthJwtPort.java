package com.logistic.auth.application.port.out.support.jwt;

import com.logistic.auth.domain.TokenPair;
import com.logistic.auth.domain.vo.UserId;

public interface AuthJwtPort {
  TokenPair createTokenPair(String sessionId, UserId userId);
}