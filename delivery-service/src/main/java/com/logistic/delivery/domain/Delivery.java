package com.logistic.delivery.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Delivery {
  private Long id;
  private Long orderId;
  private DeliveryStatus status;
  private Long departHubId;
  private Long arrivalHubId;
  private String driverId;

  public static Delivery create(
      Long orderId,
      DeliveryStatus status,
      Long departHubId,
      Long arrivalHubId,
      String driverId
  ){
    return Delivery.builder()
        .orderId(orderId)
        .status(status)
        .departHubId(departHubId)
        .arrivalHubId(arrivalHubId)
        .driverId(driverId)
        .build();
  }
}
