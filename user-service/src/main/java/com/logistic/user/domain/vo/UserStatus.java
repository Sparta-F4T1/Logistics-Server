package com.logistic.user.domain.vo;

import com.logistic.user.domain.exception.UserServiceErrorCode;
import com.logistic.user.domain.exception.UserServiceException;
import java.util.List;

public enum UserStatus {
  ACTIVE("활성", true),
  INACTIVE("비활성", false),
  LOCKED("잠금", false),
  PENDING("대기", false);

  private final String displayName;
  private final boolean usable;

  UserStatus(String displayName, boolean usable) {
    this.displayName = displayName;
    this.usable = usable;
  }

  public static UserStatus fromString(String status) {
    try {
      return UserStatus.valueOf(status.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw UserServiceException.user(
          UserServiceErrorCode.INVALID_USER_STATUS,
          "유효하지 않은 사용자 상태입니다: " + status
      );
    }
  }

  public static List<UserStatus> getActiveStatuses() {
    return List.of(ACTIVE);
  }

  public static List<UserStatus> getNonActiveStatuses() {
    return List.of(INACTIVE, LOCKED, PENDING);
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public boolean isUsable() {
    return this.usable;
  }

  public boolean isActive() {
    return this == ACTIVE;
  }

  public boolean isPending() {
    return this == PENDING;
  }

  public boolean isLocked() {
    return this == LOCKED;
  }
}