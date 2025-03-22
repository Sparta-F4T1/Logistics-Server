package com.logistic.company.domain.exception;

import static com.logistic.company.domain.exception.code.ErrorCode.COMPANY_NOT_FOUND;
import static com.logistic.company.domain.exception.code.ErrorCode.GPS_NOT_FOUND;
import static com.logistic.company.domain.exception.code.ErrorCode.HUB_NOT_FOUND;
import static com.logistic.company.domain.exception.code.ErrorCode.USER_NOT_FOUND;

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
}
