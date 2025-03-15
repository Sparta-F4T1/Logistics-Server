package com.logistic.delivery.adaptor.in.web.response;

import com.logistic.delivery.domain.DeliveryStatus;
import lombok.Builder;

@Builder
public record DeliveryResponse(
  Long deliveryId,
  Long orderId,
  DeliveryStatus status,
  Long departHubId,
  Long arrivalHubId,
  String driverId
) {
}