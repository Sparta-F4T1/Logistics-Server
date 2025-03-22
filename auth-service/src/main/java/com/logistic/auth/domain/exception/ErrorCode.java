package com.logistic.auth.domain.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
  HttpStatus getHttpStatus();

  String getCode();

  String getMessage();
}