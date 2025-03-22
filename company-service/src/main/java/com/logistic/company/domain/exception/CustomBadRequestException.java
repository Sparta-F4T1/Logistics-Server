package com.logistic.company.domain.exception;


import static com.logistic.company.domain.exception.code.ErrorCode.INVALID_ADDRESS;

import lombok.Getter;

@Getter
public class CustomBadRequestException extends RuntimeException {

  private final String code;

  public CustomBadRequestException(String code, String message) {
    super(message);
    this.code = code;
  }

  public static class InvalidAddressBadRequestException extends CustomBadRequestException {
    public InvalidAddressBadRequestException(String message) {
      super(INVALID_ADDRESS.getCode(), message);
    }
  }

}

