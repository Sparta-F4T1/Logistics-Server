package com.logistic.user.domain.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserServiceErrorCode implements ErrorCode {
  USER_NOT_FOUND(BAD_REQUEST, "AA001", "해당 사용자를 찾을 수 없습니다."),
  NOT_NULL_USER_NAME(BAD_REQUEST, "AA002", "이름은 빈 값일 수 없습니다."),
  INVALID_USER_NAME(BAD_REQUEST, "AA003", "이름 형식이 올바르지 않습니다."),
  NOT_NULL_USER_EMAIL(BAD_REQUEST, "AA004", "이메일은 빈 값일 수 없습니다."),
  INVALID_USER_EMAIL(BAD_REQUEST, "AA005", "이메일 형식이 올바르지 않습니다."),
  DUPLICATE_USER_EMAIL(BAD_REQUEST, "AA006", "이미 사용 중인 이메일입니다."),
  NOT_NULL_USER_PASSWORD(BAD_REQUEST, "AA007", "비밀번호는 빈 값일 수 없습니다."),
  INVALID_USER_PASSWORD(BAD_REQUEST, "AA008", "비밀번호 형식이 올바르지 않습니다."),
  INVALID_USER_STATUS(BAD_REQUEST, "AA009", "유효하지 않은 사용자 상태입니다."),
  NOT_NULL_ROLE_ID(BAD_REQUEST, "AA010", "역할 ID는 비어있을 수 없습니다."),
  NOT_NULL_ROLE_NAME(BAD_REQUEST, "AA011", "역할 이름은 비어있을 수 없습니다."),
  PASSWORD_NOT_MATCH(BAD_REQUEST, "AA012", "비밀번호가 일치하지 않습니다."),
  NOT_NULL_USER_ID(BAD_REQUEST, "AA013", "사용자 ID는 비어있을 수 없습니다."),
  ;

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;
}
