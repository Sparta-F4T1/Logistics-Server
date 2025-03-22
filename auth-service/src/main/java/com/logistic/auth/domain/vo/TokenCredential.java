package com.logistic.auth.domain.vo;

import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;
import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class TokenCredential {
  private final String tokenValue;
  private final TokenType tokenType;
  private final TokenClaims claims;

  private TokenCredential(String tokenValue,
                          TokenType tokenType,
                          String subject,
                          Instant issuedAt,
                          Instant expiration) {
    validateTokenValue(tokenValue);
    validateTokenType(tokenType);

    this.tokenValue = tokenValue;
    this.tokenType = tokenType;
    this.claims = TokenClaims.of(subject, issuedAt, expiration);
  }

  public static TokenCredential accessTokenOf(String tokenValue,
                                              String subject,
                                              Instant issuedAt,
                                              Instant expiration) {
    return new TokenCredential(tokenValue,
        TokenType.ACCESS,
        subject,
        issuedAt,
        expiration);
  }

  public static TokenCredential refreshTokenOf(String tokenValue,
                                               String subject,
                                               Instant issuedAt,
                                               Instant expiration) {
    return new TokenCredential(tokenValue,
        TokenType.REFRESH,
        subject,
        issuedAt,
        expiration);
  }

  private void validateTokenValue(String tokenValue) {
    if (tokenValue == null || tokenValue.isBlank()) {
      throw new AuthServiceException(AuthServiceErrorCode.EMPTY_TOKEN_VALUE);
    }
  }

  private void validateTokenType(TokenType tokenType) {
    if (tokenType == null) {
      throw new AuthServiceException(AuthServiceErrorCode.INVALID_TOKEN_TYPE);
    }
  }

  public boolean isAccessToken() {
    return this.tokenType == TokenType.ACCESS;
  }

  public boolean isRefreshToken() {
    return this.tokenType == TokenType.REFRESH;
  }
}