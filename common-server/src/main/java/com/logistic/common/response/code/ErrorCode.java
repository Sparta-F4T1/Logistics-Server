package com.logistic.common.response.code;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {

  // Common Error
  INVALID_INPUT_VALUE("C001", "잘못된 입력값입니다."),
  METHOD_NOT_ALLOWED( "C002", "요청 메서드가 허용되지 않습니다."),
  ENTITY_NOT_FOUND( "C003", "Entity가 존재하지 않습니다."),
  INTERNAL_SERVER_ERROR( "C004", "서버에서 오류가 발생했습니다."),
  INVALID_TYPE_VALUE("C005", "잘못된 타입입니다."),
  HANDLE_ACCESS_DENIED( "C006", "권한이 없습니다."),
  INVALID_JSON_FORMAT( "C007", "JSON 형식과 맞지 않습니다."),
  INVALID_REQUEST_PARAMETER( "C008", "요청 파라미터의 타입이 잘못되었습니다."),
  FAIL_REQUEST_PARAMETER_VALIDATION( "C009", "요청 파라미터의 유효성이 맞지 않습니다."),
  BIND_ERROR("C010", "바인딩 에러가 발생했습니다. 파라미터를 확인해주세요."),
  ILLEGAL_ARGUMENT("C011", "illegalArgument error"),
  NOT_FOUND("C012", "페이지를 찾을 수 없습니다."),
  INVALID_ACCESS("C013", "권한이 존재하지 않습니다.");

  private final String code;
  private String message;

  ErrorCode(final String code, final String message) {
    this.code = code;
    this.message = message;
  }

  public void updateIllegalArgumentExceptionMessage(final String message) {
    if (this == ILLEGAL_ARGUMENT) {
      this.message = message;
    } else {
      throw new UnsupportedOperationException("Cannot set message for this error code");
    }
  }
}
