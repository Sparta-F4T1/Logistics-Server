package com.logistic.driver.domain.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

  HUB_NOT_FOUND("H001", "해당 허브가 존재하지 않습니다."),
  USER_NOT_FOUND("U001", "해당 유저가 존재하지 않습니다."),
  COMPANY_NOT_FOUND("C001", "해당 업체가 존재하지 않습니다."),
  GPS_NOT_FOUND("G001", "해당 GPS가 존재하지 않습니다."),

  DRIVER_NOT_FOUND("DR001", "해당 배송담당자가 존재하지 않습니다."),
  INVALID_ADDRESS("DR002", "해당 주소가 유효하지 않습니다."),
  CREATE_DRIVER_FORBIDDEN("DR003", "배송담당자를 생성할 권한이 없습니다."),
  UPDATE_DRIVER_FORBIDDEN("DR004", "배송담당자를 수정할 권한이 없습니다."),
  DELETE_DRIVER_FORBIDDEN("DR005", "배송담당자를 삭제할 권한이 없습니다."),
  ACCESS_DRIVER_FORBIDDEN("DR006", "배송담당자를 조회할 권한이 없습니다.");

  private final String code;
  private final String message;

  ErrorCode(final String code, final String message) {
    this.code = code;
    this.message = message;
  }

}
