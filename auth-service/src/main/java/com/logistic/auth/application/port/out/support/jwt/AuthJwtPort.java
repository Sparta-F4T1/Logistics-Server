package com.logistic.auth.application.port.out.support.jwt;

import com.logistic.auth.domain.TokenPair;
import com.logistic.auth.domain.service.TokenValidationResult;
import com.logistic.auth.domain.vo.UserId;
import java.time.Instant;

public interface AuthJwtPort {
  TokenPair createTokenPair(String sessionId, UserId userId);

  Instant getExpirationTime(String s);

  TokenValidationResult validateTokenAndExtractId(String token);
}