package com.logistic.auth.domain.exception;

import lombok.Getter;

@Getter
public class JwtExpiredException extends AuthServiceException {
  public JwtExpiredException(AuthServiceErrorCode error) {
    super(error);
  }

  public JwtExpiredException(AuthServiceErrorCode error, Throwable cause) {
    super(error, cause);
  }
}