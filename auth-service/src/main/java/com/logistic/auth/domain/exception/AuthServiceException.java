package com.logistic.auth.domain.exception;

import lombok.Getter;

@Getter
public class AuthServiceException extends RuntimeException {

  private final AuthServiceErrorCode error;

  protected AuthServiceException(AuthServiceErrorCode error) {
    super(error.getMessage());
    this.error = error;
  }

  protected AuthServiceException(AuthServiceErrorCode error, Throwable cause) {
    super(error.getMessage(), cause);
    this.error = error;
  }

  protected AuthServiceException(AuthServiceErrorCode error, String detailMessage) {
    super(detailMessage);
    this.error = error;
  }

  private static AuthServiceException create(AuthServiceErrorCode error, String category) {
    validateErrorCodeCategory(error, category);
    return new AuthServiceException(error);
  }

  private static AuthServiceException create(AuthServiceErrorCode error, String category, String message) {
    validateErrorCodeCategory(error, category);
    return new AuthServiceException(error, message);
  }

  public static AuthServiceException auth(AuthServiceErrorCode error) {
    return create(error, "AA");
  }

  public static AuthServiceException auth(AuthServiceErrorCode error, String message) {
    return create(error, "AA", message);
  }

  public static AuthServiceException jwt(AuthServiceErrorCode error) {
    return create(error, "AJ");
  }

  public static AuthServiceException token(AuthServiceErrorCode error) {
    return create(error, "AT");
  }

  public static AuthServiceException token(AuthServiceErrorCode error, String message) {
    return create(error, "AT", message);
  }

  public static AuthServiceException session(AuthServiceErrorCode error) {
    return create(error, "AS");
  }

  public static AuthServiceException user(AuthServiceErrorCode error) {
    return create(error, "AU");
  }

  public static AuthServiceException user(AuthServiceErrorCode error, String message) {
    return create(error, "AU", message);
  }

  public static AuthServiceException etc(AuthServiceErrorCode error) {
    return create(error, "AE");
  }

  public static AuthServiceException etc(AuthServiceErrorCode error, String message) {
    return create(error, "AE", message);
  }

  private static void validateErrorCodeCategory(AuthServiceErrorCode error, String expectedCategory) {
    if (!error.getCode().startsWith(expectedCategory)) {
      throw new IllegalArgumentException(
          String.format("에러 코드 %s는 %s 카테고리에 속하지 않습니다", error.getCode(), expectedCategory));
    }
  }
}