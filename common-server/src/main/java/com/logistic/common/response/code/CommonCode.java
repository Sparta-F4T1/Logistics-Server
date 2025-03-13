package com.logistic.common.response.code;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum CommonCode {

  COMMON_SUCCESS("S001", "요청에 대한 응답이 완료되었습니다.");

  private final String code;
  private String message;

  CommonCode(final String code, final String message) {
    this.code = code;
    this.message = message;
  }
}
