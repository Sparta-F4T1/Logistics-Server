package com.logistic.driver.domain.exception;

import static com.logistic.driver.domain.exception.ErrorCode.ACCESS_DRIVER_FORBIDDEN;
import static com.logistic.driver.domain.exception.ErrorCode.CREATE_DRIVER_FORBIDDEN;
import static com.logistic.driver.domain.exception.ErrorCode.DELETE_DRIVER_FORBIDDEN;
import static com.logistic.driver.domain.exception.ErrorCode.UPDATE_DRIVER_FORBIDDEN;

import lombok.Getter;

@Getter
public class CustomForbiddenException extends RuntimeException {

  private final String code;

  public CustomForbiddenException(String code, String message) {
    super(message);
    this.code = code;
  }

  public static class CreateDriverForbiddenException extends CustomForbiddenException {
    public CreateDriverForbiddenException() {
      super(CREATE_DRIVER_FORBIDDEN.getCode(), CREATE_DRIVER_FORBIDDEN.getMessage());
    }
  }

  public static class UpdateDriverForbiddenException extends CustomForbiddenException {
    public UpdateDriverForbiddenException() {
      super(UPDATE_DRIVER_FORBIDDEN.getCode(), UPDATE_DRIVER_FORBIDDEN.getMessage());
    }
  }

  public static class DeleteDriverForbiddenException extends CustomForbiddenException {
    public DeleteDriverForbiddenException() {
      super(DELETE_DRIVER_FORBIDDEN.getCode(), DELETE_DRIVER_FORBIDDEN.getMessage());
    }
  }

  public static class AccessDriverForbiddenException extends CustomForbiddenException {
    public AccessDriverForbiddenException() {
      super(ACCESS_DRIVER_FORBIDDEN.getCode(), ACCESS_DRIVER_FORBIDDEN.getMessage());
    }
  }
}