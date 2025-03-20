package com.logistic.auth.domain.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public class JwtParsingException extends RuntimeException {

  private final JwtErrorCode error;

  public JwtParsingException(JwtErrorCode error) {
    super(error.getMessage());
    this.error = error;
  }

  public JwtParsingException(JwtErrorCode error, Throwable cause) {
    super(error.getMessage(), cause);
    this.error = error;
  }

  public JwtParsingException(String message, Throwable cause) {
    super(message, cause);
    this.error = JwtErrorCode.GENERAL_ERROR;
  }

  @Getter
  @RequiredArgsConstructor
  public enum JwtErrorCode implements ErrorCode {
    INVALID_SIGNATURE(BAD_REQUEST, "J001", "유효하지 않는 JWT 서명입니다."),
    UNSUPPORTED_TOKEN(BAD_REQUEST, "J003", "지원되지 않는 JWT 토큰입니다."),
    MALFORMED_TOKEN(BAD_REQUEST, "J004", "잘못된 형식의 JWT 토큰입니다."),
    EMPTY_CLAIMS(BAD_REQUEST, "J005", "JWT 클레임이 비어있습니다."),
    GENERAL_ERROR(BAD_REQUEST, "J999", "JWT 처리 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
  }
}