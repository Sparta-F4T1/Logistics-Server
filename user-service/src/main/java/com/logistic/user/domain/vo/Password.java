package com.logistic.user.domain.vo;

import com.logistic.user.domain.exception.UserServiceErrorCode;
import com.logistic.user.domain.exception.UserServiceException;

public record Password(String value) {
  private static final int MIN_LENGTH = 8;
  private static final int MAX_LENGTH = 20;
  private static final String PASSWORD_REGEX =
      "^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{" + MIN_LENGTH + "," + MAX_LENGTH
          + "}$";
  private static final String INVALID_PASSWORD_FORMAT_MESSAGE =
      "비밀번호는 " + MIN_LENGTH + "~" + MAX_LENGTH + "자 사이며, 대문자, 소문자, 특수문자를 모두 포함해야 합니다.";

  public Password {
    validate(value);
  }

  private void validate(String value) {
    if (value == null || value.isBlank()) {
      throw UserServiceException.user(UserServiceErrorCode.NOT_NULL_USER_PASSWORD);
    }

    if (!value.matches(PASSWORD_REGEX)) {
      throw UserServiceException.user(UserServiceErrorCode.INVALID_USER_PASSWORD, INVALID_PASSWORD_FORMAT_MESSAGE);
    }
  }
}