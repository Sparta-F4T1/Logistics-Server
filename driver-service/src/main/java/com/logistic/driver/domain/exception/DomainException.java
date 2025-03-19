package com.logistic.driver.domain.exception;

import static com.logistic.driver.domain.exception.DomainErrorCode.DRIVER_NOT_FOUND;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
  private final String code;

  public DomainException(String code, String message) {
    super(message);
    this.code = code;
  }

  public static class DomainNotFoundException extends DomainException {
    public DomainNotFoundException() {
      super(DRIVER_NOT_FOUND.getCode(), DRIVER_NOT_FOUND.getMessage());
    }
  }

  public static class HubNotFoundException extends DomainException {
    public HubNotFoundException() {
      super(DRIVER_NOT_FOUND.getCode(), DRIVER_NOT_FOUND.getMessage());
    }
  }
}
