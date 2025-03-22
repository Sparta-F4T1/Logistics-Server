package com.logistic.user.domain.vo;

import com.logistic.user.domain.exception.UserServiceErrorCode;
import com.logistic.user.domain.exception.UserServiceException;

public record UserId(String value) {
  public UserId {
    validate(value);
  }

  public static UserId of(String value) {
    return new UserId(value);
  }

  private void validate(String value) {
    if (value == null || value.isBlank()) {
      throw UserServiceException.user(UserServiceErrorCode.NOT_NULL_USER_ID);
    }
  }
}