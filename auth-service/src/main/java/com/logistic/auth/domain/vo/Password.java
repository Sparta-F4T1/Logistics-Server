package com.logistic.auth.domain.vo;

import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
  private String hashedValue;

  private Password(String hashedValue) {
    this.hashedValue = hashedValue;
  }

  public static Password of(String encodedPassword) {
    if (encodedPassword == null || encodedPassword.trim().isEmpty()) {
      throw AuthServiceException.user(AuthServiceErrorCode.EMPTY_PASSWORD);
    }
    return new Password(encodedPassword);
  }
}