package com.logistic.delivery.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Delivery {
  private Long deliveryId;
  private Long orderId;
  private DeliveryStatus deliveryStatus;
  private Long departHubId;
  private Long arrivalHubId;
  private String driverId;

  public static Delivery create(
      Long deliveryId,
      Long orderId,
      DeliveryStatus deliveryStatus,
      Long departHubId,
      Long arrivalHubId,
      String driverId
  ){
    return Delivery.builder()
        .departHubId(deliveryId)
        .orderId(orderId)
        .deliveryStatus(deliveryStatus)
        .departHubId(departHubId)
        .arrivalHubId(arrivalHubId)
        .driverId(driverId)
        .build();
  }
}
