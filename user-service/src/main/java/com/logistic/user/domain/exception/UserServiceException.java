package com.logistic.user.domain.exception;

import lombok.Getter;

@Getter
public class UserServiceException extends RuntimeException {

  private final UserServiceErrorCode error;

  protected UserServiceException(UserServiceErrorCode error) {
    super(error.getMessage());
    this.error = error;
  }

  protected UserServiceException(UserServiceErrorCode error, Throwable cause) {
    super(error.getMessage(), cause);
    this.error = error;
  }

  protected UserServiceException(UserServiceErrorCode error, String detailMessage) {
    super(detailMessage);
    this.error = error;
  }

  private static UserServiceException create(UserServiceErrorCode error, String category) {
    validateErrorCodeCategory(error, category);
    return new UserServiceException(error);
  }

  private static UserServiceException create(UserServiceErrorCode error, String category, String message) {
    validateErrorCodeCategory(error, category);
    return new UserServiceException(error, message);
  }

  public static UserServiceException user(UserServiceErrorCode error) {
    return create(error, "AU");
  }

  public static UserServiceException user(UserServiceErrorCode error, String message) {
    return create(error, "AU", message);
  }

  private static void validateErrorCodeCategory(UserServiceErrorCode error, String expectedCategory) {
    if (!error.getCode().startsWith(expectedCategory)) {
      throw new IllegalArgumentException(
          String.format("에러 코드 %s는 %s 카테고리에 속하지 않습니다", error.getCode(), expectedCategory));
    }
  }
}