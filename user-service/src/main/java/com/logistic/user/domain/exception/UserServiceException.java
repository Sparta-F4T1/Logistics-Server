package com.logistic.user.domain.exception;

import lombok.Getter;

@Getter
public class UserServiceException extends RuntimeException {

  private final UserServiceErrorCode error;

  public UserServiceException(UserServiceErrorCode error) {
    super(error.getMessage());
    this.error = error;
  }

  public UserServiceException(UserServiceErrorCode error, String detailMessage) {
    super(detailMessage);
    this.error = error;
  }

  public UserServiceException(UserServiceErrorCode error, Throwable cause) {
    super(error.getMessage(), cause);
    this.error = error;
  }

  public static UserServiceException user(UserServiceErrorCode error) {
    validateErrorCodeCategory(error, "U");
    return new UserServiceException(error);
  }

  public static UserServiceException user(UserServiceErrorCode error, String detailMessage) {
    validateErrorCodeCategory(error, "U");
    return new UserServiceException(error, detailMessage);
  }

  private static void validateErrorCodeCategory(ErrorCode error, String expectedCategory) {
    String category = error.getCode().substring(0, 1);
    if (!category.equals(expectedCategory)) {
      throw new IllegalArgumentException(
          String.format("에러 코드 %s는 %s 카테고리에 속하지 않습니다", error.getCode(), expectedCategory));
    }
  }
}
