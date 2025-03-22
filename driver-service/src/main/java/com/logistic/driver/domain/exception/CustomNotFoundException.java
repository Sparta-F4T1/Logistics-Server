package com.logistic.driver.domain.exception;

import static com.logistic.driver.domain.exception.ErrorCode.COMPANY_NOT_FOUND;
import static com.logistic.driver.domain.exception.ErrorCode.DRIVER_NOT_FOUND;
import static com.logistic.driver.domain.exception.ErrorCode.GPS_NOT_FOUND;
import static com.logistic.driver.domain.exception.ErrorCode.HUB_NOT_FOUND;
import static com.logistic.driver.domain.exception.ErrorCode.USER_NOT_FOUND;

import lombok.Getter;

@Getter
public class CustomNotFoundException extends RuntimeException {

  private final String code;

  public CustomNotFoundException(String code, String message) {
    super(message);
    this.code = code;
  }

  public static class CompanyNotFoundException extends CustomNotFoundException {
    public CompanyNotFoundException() {
      super(COMPANY_NOT_FOUND.getCode(), COMPANY_NOT_FOUND.getMessage());
    }
  }

  public static class HubNotFoundException extends CustomNotFoundException {
    public HubNotFoundException() {
      super(HUB_NOT_FOUND.getCode(), HUB_NOT_FOUND.getMessage());
    }
  }

  public static class UserNotFoundException extends CustomNotFoundException {
    public UserNotFoundException() {
      super(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage());
    }
  }

  public static class GpsNotFoundException extends CustomNotFoundException {
    public GpsNotFoundException() {
      super(GPS_NOT_FOUND.getCode(), GPS_NOT_FOUND.getMessage());
    }
  }

  public static class DriverNotFoundException extends CustomNotFoundException {
    public DriverNotFoundException() {
      super(DRIVER_NOT_FOUND.getCode(), DRIVER_NOT_FOUND.getMessage());
    }
  }
}