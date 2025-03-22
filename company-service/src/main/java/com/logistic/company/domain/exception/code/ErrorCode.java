package com.logistic.company.domain.exception.code;

import lombok.Getter;

@Getter
public enum ErrorCode {

  COMPANY_NOT_FOUND("C001", "해당 업체가 존재하지 않습니다."),
  INVALID_ADDRESS("C002", "해당 주소가 유효하지 않습니다."),
  CREATE_COMPANY_FORBIDDEN("C003", "업체를 생성할 권한이 없습니다."),
  UPDATE_COMPANY_FORBIDDEN("C004", "업체를 수정할 권한이 없습니다."),
  DELETE_COMPANY_FORBIDDEN("C005", "업체를 삭제할 권한이 없습니다."),
  ACCESS_COMPANY_FORBIDDEN("C006", "업체를 조회할 권한이 없습니다."),

  HUB_NOT_FOUND("H001", "해당 허브가 존재하지 않습니다."),
  USER_NOT_FOUND("U001", "해당 유저가 존재하지 않습니다."),
  GPS_NOT_FOUND("G001", "해당 GPS가 존재하지 않습니다.");

  private final String code;
  private final String message;

  ErrorCode(final String code, final String message) {
    this.code = code;
    this.message = message;
  }

}
