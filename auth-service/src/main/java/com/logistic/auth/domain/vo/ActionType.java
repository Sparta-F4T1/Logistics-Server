package com.logistic.auth.domain.vo;

import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;

public enum ActionType {
  CREATE("생성"),
  READ("조회"),
  UPDATE("수정"),
  DELETE("삭제"),
  ;
  private final String description;

  ActionType(String description) {
    this.description = description;
  }

  public static ActionType fromString(String type) {
    try {
      if (type == null) {
        throw new IllegalArgumentException();
      }
      return ActionType.valueOf(type.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw AuthServiceException.auth(
          AuthServiceErrorCode.INVALID_ACTION_TYPE,
          "유효하지 않은 액션 타입입니다: " + type
      );
    }
  }

  public String getDescription() {
    return description;
  }
}