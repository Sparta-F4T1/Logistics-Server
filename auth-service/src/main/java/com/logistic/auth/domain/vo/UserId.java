package com.logistic.auth.domain.vo;

import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;

public record UserId(String value) {

  public UserId {
    validateUserId(value);
  }

  public static UserId of(String value) {
    return new UserId(value);
  }

  private static void validateUserId(String value) {
    if (value == null || value.isBlank()) {
      throw AuthServiceException.token(AuthServiceErrorCode.EMPTY_SUBJECT, "사용자의 아이디가 비어있습니다.");
    }
  }
}