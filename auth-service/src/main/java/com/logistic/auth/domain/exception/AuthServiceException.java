package com.logistic.auth.domain.exception;


import lombok.Getter;

@Getter
public class AuthServiceException extends RuntimeException {

  private final AuthServiceErrorCode error;

  public AuthServiceException(AuthServiceErrorCode error) {
    super(error.getMessage());
    this.error = error;
  }

  public AuthServiceException(AuthServiceErrorCode error, String detailMessage) {
    super(detailMessage);
    this.error = error;
  }

  public AuthServiceException(AuthServiceErrorCode error, Throwable cause) {
    super(error.getMessage(), cause);
    this.error = error;
  }

  /**
   * 특정 카테고리의 에러 코드에 대한 예외를 생성하는 팩토리 메서드들
   */
  public static AuthServiceException auth(AuthServiceErrorCode error) {
    validateErrorCodeCategory(error, "A");
    return new AuthServiceException(error);
  }

  public static AuthServiceException jwt(AuthServiceErrorCode error) {
    validateErrorCodeCategory(error, "J");
    return new AuthServiceException(error);
  }

  public static AuthServiceException token(AuthServiceErrorCode error) {
    validateErrorCodeCategory(error, "T");
    return new AuthServiceException(error);
  }

  public static AuthServiceException session(AuthServiceErrorCode error) {
    validateErrorCodeCategory(error, "S");
    return new AuthServiceException(error);
  }

  public static AuthServiceException user(AuthServiceErrorCode error) {
    validateErrorCodeCategory(error, "U");
    return new AuthServiceException(error);
  }

  private static void validateErrorCodeCategory(AuthServiceErrorCode error, String expectedCategory) {
    String category = error.getCode().substring(0, 1);
    if (!category.equals(expectedCategory)) {
      throw new IllegalArgumentException(
          String.format("에러 코드 %s는 %s 카테고리에 속하지 않습니다", error.getCode(), expectedCategory));
    }
  }
}