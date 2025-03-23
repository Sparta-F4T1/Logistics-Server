package com.logistic.user.domain.vo;

import com.logistic.user.domain.exception.UserServiceErrorCode;
import com.logistic.user.domain.exception.UserServiceException;

public record UserRole(Long roleId, String name) {
  public UserRole {
    validate(roleId, name);
  }

  public static UserRole of(Long roleId, String name) {
    return new UserRole(roleId, name);
  }

  private void validate(Long roleId, String name) {
    if (roleId == null) {
      throw UserServiceException.user(UserServiceErrorCode.NOT_NULL_ROLE_ID);
    }

    if (name == null || name.isBlank()) {
      throw UserServiceException.user(UserServiceErrorCode.NOT_NULL_ROLE_NAME);
    }
  }
}