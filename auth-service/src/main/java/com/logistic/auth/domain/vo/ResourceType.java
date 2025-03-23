package com.logistic.auth.domain.vo;

import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;

public enum ResourceType {
  USER("사용자"),
  COMPANY("업체"),
  PRODUCT("상품"),
  HUB("허브"),
  DELIVERY("배송"),
  HUB_DELIVERY_HISTORY("허브 배송 이력"),
  DRIVER("드라이버"),
  ORDER("주문"),
  SLACK_MESSAGE("슬랙 메시지");

  private final String description;

  ResourceType(String description) {
    this.description = description;
  }

  public static ResourceType fromString(String type) {
    try {
      if (type == null) {
        throw new IllegalArgumentException();
      }
      return ResourceType.valueOf(type.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw AuthServiceException.auth(
          AuthServiceErrorCode.INVALID_RESOURCE_TYPE,
          "유효하지 않은 리소스 타입입니다: " + type
      );
    }
  }

  public String getDescription() {
    return description;
  }
}