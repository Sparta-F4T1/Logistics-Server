package com.logistic.auth.domain.exception;

import lombok.Getter;

@Getter
public class JwtParsingException extends AuthServiceException {
  public JwtParsingException(AuthServiceErrorCode error) {
    super(error);
  }
}