package com.logistic.company.domain.exception;

import static com.logistic.company.domain.exception.code.ErrorCode.ACCESS_COMPANY_FORBIDDEN;
import static com.logistic.company.domain.exception.code.ErrorCode.CREATE_COMPANY_FORBIDDEN;
import static com.logistic.company.domain.exception.code.ErrorCode.DELETE_COMPANY_FORBIDDEN;
import static com.logistic.company.domain.exception.code.ErrorCode.UPDATE_COMPANY_FORBIDDEN;

import lombok.Getter;

@Getter
public class CustomForbiddenException extends RuntimeException {

  private final String code;

  public CustomForbiddenException(String code, String message) {
    super(message);
    this.code = code;
  }

  public static class CreateCompanyForbiddenException extends CustomForbiddenException {
    public CreateCompanyForbiddenException() {
      super(CREATE_COMPANY_FORBIDDEN.getCode(), CREATE_COMPANY_FORBIDDEN.getMessage());
    }
  }

  public static class UpdateCompanyForbiddenException extends CustomForbiddenException {
    public UpdateCompanyForbiddenException() {
      super(UPDATE_COMPANY_FORBIDDEN.getCode(), UPDATE_COMPANY_FORBIDDEN.getMessage());
    }
  }

  public static class DeleteCompanyForbiddenException extends CustomForbiddenException {
    public DeleteCompanyForbiddenException() {
      super(DELETE_COMPANY_FORBIDDEN.getCode(), DELETE_COMPANY_FORBIDDEN.getMessage());
    }
  }

  public static class AccessCompanyForbiddenException extends CustomForbiddenException {
    public AccessCompanyForbiddenException() {
      super(ACCESS_COMPANY_FORBIDDEN.getCode(), ACCESS_COMPANY_FORBIDDEN.getMessage());
    }
  }
}
