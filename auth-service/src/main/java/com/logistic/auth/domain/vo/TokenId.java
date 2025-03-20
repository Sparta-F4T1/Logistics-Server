package com.logistic.auth.domain.vo;

import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;

public record TokenId(String value) {

  public TokenId {
    validateTokenId(value);
  }

  public static TokenId of(String value) {
    return new TokenId(value);
  }

  private static void validateTokenId(String value) {
    if (value == null || value.isBlank()) {
      throw new AuthServiceException(AuthServiceErrorCode.EMPTY_TOKEN_ID);
    }
  }
}