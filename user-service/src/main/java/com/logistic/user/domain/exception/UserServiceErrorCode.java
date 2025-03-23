package com.logistic.user.domain.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserServiceErrorCode implements ErrorCode {
  USER_NOT_FOUND(BAD_REQUEST, "UA001", "해당 사용자를 찾을 수 없습니다."),
  NOT_NULL_USER_NAME(BAD_REQUEST, "UA002", "이름은 빈 값일 수 없습니다."),
  INVALID_USER_NAME(BAD_REQUEST, "UA003", "이름 형식이 올바르지 않습니다."),
  NOT_NULL_USER_EMAIL(BAD_REQUEST, "UA004", "이메일은 빈 값일 수 없습니다."),
  INVALID_USER_EMAIL(BAD_REQUEST, "UA005", "이메일 형식이 올바르지 않습니다."),
  DUPLICATE_USER_EMAIL(BAD_REQUEST, "UA006", "이미 사용 중인 이메일입니다."),
  NOT_NULL_USER_PASSWORD(BAD_REQUEST, "UA007", "비밀번호는 빈 값일 수 없습니다."),
  INVALID_USER_PASSWORD(BAD_REQUEST, "UA008", "비밀번호 형식이 올바르지 않습니다."),
  INVALID_USER_STATUS(BAD_REQUEST, "UA009", "유효하지 않은 사용자 상태입니다."),
  NOT_NULL_ROLE_ID(BAD_REQUEST, "UA010", "역할 ID는 비어있을 수 없습니다."),
  NOT_NULL_ROLE_NAME(BAD_REQUEST, "UA011", "역할 이름은 비어있을 수 없습니다."),
  PASSWORD_NOT_MATCH(BAD_REQUEST, "UA012", "비밀번호가 일치하지 않습니다."),
  NOT_NULL_USER_ID(BAD_REQUEST, "UA013", "사용자 ID는 비어있을 수 없습니다."),
  DUPLICATE_USER_ID(BAD_REQUEST, "UA014", "이미 존재하는 사용자 ID입니다."),
  DUPLICATE_SLACK_ACCOUNT(BAD_REQUEST, "UA015", "이미 사용 중인 슬랙 계정입니다."),
  INVALID_ROLE_TYPE(BAD_REQUEST, "UA016", "유효하지 않은 역할 타입입니다."),
  NOT_FOUND_USER(NOT_FOUND, "UA017", "사용자를 찾을 수 없습니다."),
  INVALID_STATUS_TRANSITION(BAD_REQUEST, "UA018", "잘못된 상태 전환 시도"),
  PROTECTED_USER_ROLE(FORBIDDEN, "UA019", "보호된 역할을 가진 사용자는 삭제할 수 없습니다."),
  USER_ALREADY_INACTIVE(BAD_REQUEST, "UA20", "이미 비활성화되었거나 잠긴 사용자입니다."),
  ;

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;
}
