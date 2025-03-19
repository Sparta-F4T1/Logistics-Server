package com.logistic.driver.domain.exception;

import lombok.Getter;

@Getter
public enum DomainErrorCode {

  DRIVER_NOT_FOUND("DR001", "해당 Driver가 존재하지 않습니다."),
  HUB_NOT_FOUND("H001", "해당 Hub가 존재하지 않습니다."),
  METHOD_NOT_ALLOWED("DR002", "요청 메서드가 허용되지 않습니다."),
  INVALID_ACCESS("DR03", "권한이 존재하지 않습니다.");

  private final String code;
  private final String message;

  DomainErrorCode(final String code, final String message) {
    this.code = code;
    this.message = message;
  }

}
