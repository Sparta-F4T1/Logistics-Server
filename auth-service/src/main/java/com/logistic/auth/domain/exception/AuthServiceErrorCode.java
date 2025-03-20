package com.logistic.auth.domain.exception;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 카테고리: PREFIX: A
 * <p> AUTH (A): 일반 인증 오류
 * <p> JWT (J): JWT 토큰 관련 오류
 * <p> TOKEN (T): 토큰 관리 오류
 * <p> SESSION (S): 세션 관련 오류
 * <p> USER (U): 사용자 관리 오류
 */
@Getter
@RequiredArgsConstructor
public enum AuthServiceErrorCode implements ErrorCode {
  // 인증 및 권한 오류 (A)
  USER_NOT_FOUND(UNAUTHORIZED, "AA001", "해당 사용자를 찾을 수 없습니다."),
  EXPIRED_TOKEN(UNAUTHORIZED, "AA002", "만료된 JWT 토큰입니다."),
  INSUFFICIENT_PERMISSIONS(FORBIDDEN, "AA003", "접근 권한이 없습니다."),
  NO_ROLE_ASSIGNED(FORBIDDEN, "AA004", "사용자에게 할당된 역할이 없습니다."),
  ACCESS_DENIED(FORBIDDEN, "AA005", "리소스에 대한 접근이 거부되었습니다."),
  INVALID_REFRESH_TOKEN(UNAUTHORIZED, "AA006", "리프레시 토큰이 유효하지 않습니다."),
  REFRESH_TOKEN_MISMATCH(UNAUTHORIZED, "AA007", "저장된 리프레시 토큰과 일치하지 않습니다. 다시 로그인해주세요."),
  BLACKLISTED_TOKEN(UNAUTHORIZED, "AA008", "차단된 JWT 토큰입니다."),
  NOT_FOUND_AUTH_USER(NOT_FOUND, "AA009", "아이디 또는 비밀번호가 잘못되어 사용자를 찾을 수 없습니다."),

  // JWT 파싱 오류 (J)
  INVALID_SIGNATURE(BAD_REQUEST, "AJ001", "유효하지 않는 JWT 서명입니다."),
  UNSUPPORTED_TOKEN(BAD_REQUEST, "AJ002", "지원되지 않는 JWT 토큰입니다."),
  MALFORMED_TOKEN(BAD_REQUEST, "AJ003", "잘못된 형식의 JWT 토큰입니다."),
  EMPTY_CLAIMS(BAD_REQUEST, "AJ004", "JWT 클레임이 비어있습니다."),
  JWT_GENERAL_ERROR(BAD_REQUEST, "AJ999", "JWT 처리 중 오류가 발생했습니다."),

  // 토큰 관리 오류 (T)
  EMPTY_TOKEN_VALUE(BAD_REQUEST, "AT001", "토큰 값은 빈 값일 수 없습니다."),
  EMPTY_TOKEN_ID(BAD_REQUEST, "AT002", "토큰 ID는 빈 값일 수 없습니다."),
  EMPTY_SUBJECT(BAD_REQUEST, "AT003", "토큰 주체(Subject)는 빈 값일 수 없습니다."),
  NULL_ISSUED_AT(BAD_REQUEST, "AT004", "발급일(issuedAt)은 null일 수 없습니다."),
  NULL_EXPIRATION(BAD_REQUEST, "AT005", "만료일(expiration)은 null일 수 없습니다."),
  INVALID_EXPIRATION(BAD_REQUEST, "AT006", "만료일은 발급일보다 이후여야 합니다."),
  ALREADY_EXPIRED(BAD_REQUEST, "AT007", "토큰이 이미 만료되었습니다."),
  INVALID_TOKEN_TYPE(BAD_REQUEST, "AT008", "유효하지 않은 토큰 타입입니다."),
  INVALID_TOKEN_PAIR(BAD_REQUEST, "AT009", "액세스 토큰과 리프레시 토큰이 일치하지 않습니다."),

  // 세션 관련 오류 (S)
  EMPTY_SESSION_ID(BAD_REQUEST, "AS001", "세션 ID는 빈 값일 수 없습니다."),
  INVALID_ISSUED_AT(BAD_REQUEST, "AS002", "발급 시간은 양수여야 합니다."),
  INVALID_SESSION_EXPIRATION(BAD_REQUEST, "AS003", "만료 시간은 발급 시간보다 커야 합니다."),
  EXPIRED_SESSION(BAD_REQUEST, "AS004", "세션이 만료되었습니다."),
  INVALID_DURATION(BAD_REQUEST, "AS005", "세션 유효기간은 양수여야 합니다."),
  SESSION_NULL_ISSUED_AT(BAD_REQUEST, "AS006", "세션 정보가 필요합니다."),

  // 사용자 관련 오류 (U)
  EMPTY_USER_ID(BAD_REQUEST, "AU001", "사용자 ID는 빈 값일 수 없습니다."),
  MISSING_USER_INFO(BAD_REQUEST, "AU002", "사용자 정보는 필수입니다."),
  MISSING_ROLE(BAD_REQUEST, "AU003", "사용자 역할은 필수입니다."),
  INVALID_ROLE(BAD_REQUEST, "AU004", "유효하지 않은 역할입니다."),
  MISSING_ACCESS_SCOPE(BAD_REQUEST, "AU005", "접근 범위는 필수입니다.");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;
}