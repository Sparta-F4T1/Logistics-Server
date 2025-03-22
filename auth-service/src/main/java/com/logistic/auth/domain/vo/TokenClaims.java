package com.logistic.auth.domain.vo;

import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;
import java.time.Instant;
import lombok.Getter;

@Getter
public class TokenClaims {
  private final UserId subject;
  private final Instant issuedAt;
  private final Instant expiration;

  private TokenClaims(UserId subject, Instant issuedAt, Instant expiration) {
    this.subject = subject;
    this.issuedAt = issuedAt;
    this.expiration = expiration;
  }

  public static TokenClaims of(String subject, Instant issuedAt, Instant expiration) {
    validateInstants(issuedAt, expiration);

    UserId userId = UserId.of(subject);

    return new TokenClaims(userId, issuedAt, expiration);
  }

  private static void validateInstants(Instant issuedAt, Instant expiration) {
    if (issuedAt == null) {
      throw new AuthServiceException(AuthServiceErrorCode.NULL_ISSUED_AT);
    }
    if (expiration == null) {
      throw new AuthServiceException(AuthServiceErrorCode.NULL_EXPIRATION);
    }
    if (expiration.isBefore(issuedAt)) {
      throw new AuthServiceException(AuthServiceErrorCode.INVALID_EXPIRATION);
    }
  }
}