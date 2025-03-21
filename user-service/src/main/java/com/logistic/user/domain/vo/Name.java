package com.logistic.user.domain.vo;

import com.logistic.user.domain.exception.UserServiceErrorCode;
import com.logistic.user.domain.exception.UserServiceException;

public record Name(String value) {
  private static final int MIN_LENGTH = 2;
  private static final int MAX_LENGTH = 5;
  private static final String INVALID_LENGTH_MESSAGE = "이름은 " + MIN_LENGTH + "자 이상 " + MAX_LENGTH + "자 이하여야 합니다.";

  public Name {
    validate(value);
  }

  private void validate(String value) {
    if (value == null || value.isBlank()) {
      throw UserServiceException.user(UserServiceErrorCode.NOT_NULL_USER_NAME);
    }

    if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
      throw UserServiceException.user(UserServiceErrorCode.INVALID_USER_NAME, INVALID_LENGTH_MESSAGE);
    }
  }
}