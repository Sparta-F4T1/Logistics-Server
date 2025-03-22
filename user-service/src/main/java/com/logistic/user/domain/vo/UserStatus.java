package com.logistic.user.domain.vo;

import com.logistic.user.domain.exception.UserServiceErrorCode;
import com.logistic.user.domain.exception.UserServiceException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum UserStatus {
  ACTIVE("활성", true),
  INACTIVE("비활성", false),
  LOCKED("잠금", false),
  PENDING("대기", false);

  private static final Map<UserStatus, Set<UserStatus>> ALLOWED_TRANSITIONS;

  static {
    ALLOWED_TRANSITIONS = new HashMap<>();
    ALLOWED_TRANSITIONS.put(PENDING, EnumSet.of(ACTIVE, INACTIVE));
    ALLOWED_TRANSITIONS.put(ACTIVE, EnumSet.of(INACTIVE, LOCKED));
    ALLOWED_TRANSITIONS.put(INACTIVE, EnumSet.of(ACTIVE));
    ALLOWED_TRANSITIONS.put(LOCKED, EnumSet.of(ACTIVE, INACTIVE));
  }

  private final String displayName;
  private final boolean usable;

  UserStatus(String displayName, boolean usable) {
    this.displayName = displayName;
    this.usable = usable;
  }

  public static UserStatus transition(UserStatus currentStatus, UserStatus targetStatus) {
    if (currentStatus.canTransitionTo(targetStatus)) {
      return targetStatus;
    }
    throw UserServiceException.user(
        UserServiceErrorCode.INVALID_STATUS_TRANSITION,
        String.format("상태 전환이 불가능합니다: %s -> %s", currentStatus, targetStatus)
    );
  }

  public static UserStatus fromString(String status) {
    try {
      if (status == null) {
        throw new IllegalArgumentException();
      }
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

  public boolean canTransitionTo(UserStatus targetStatus) {
    Set<UserStatus> allowedNextStates = ALLOWED_TRANSITIONS.get(this);
    return allowedNextStates != null && allowedNextStates.contains(targetStatus);
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