package com.logistic.delivery.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {
  HUB_WAITING("허브 대기중"),
  HUB_TRANSIT("허브 이동중"),
  HUB_ARRIVED("목적지 허브 도착"),
  MOVING_TO_COMPANY("업체로 배송중"),  // 업체 이동중
  DELIVERED("배송 완료");

  private final String viewStatus;
}
