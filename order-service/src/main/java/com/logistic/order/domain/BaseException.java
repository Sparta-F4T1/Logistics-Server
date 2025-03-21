package com.logistic.order.domain;

import com.logistic.common.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
  private String message;
  private ErrorCode code;
  private HttpStatus status;

  public BaseException(String message, ErrorCode errorCode, HttpStatus status) {
    super(message);
    this.message = message;
    this.code = errorCode;
    this.status = status;
  }
}
