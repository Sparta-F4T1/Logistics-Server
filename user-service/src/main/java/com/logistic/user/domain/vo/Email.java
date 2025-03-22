package com.logistic.user.domain.vo;

import com.logistic.user.domain.exception.UserServiceErrorCode;
import com.logistic.user.domain.exception.UserServiceException;

public record Email(String value) {
  private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

  public Email {
    validate(value);
  }

  public static Email of(String value) {
    return new Email(value);
  }

  private void validate(String value) {
    if (value == null || value.isBlank()) {
      throw UserServiceException.user(UserServiceErrorCode.NOT_NULL_USER_EMAIL);
    }

    if (!value.matches(EMAIL_REGEX)) {
      throw UserServiceException.user(UserServiceErrorCode.INVALID_USER_EMAIL);
    }
  }
}