package com.logistic.delivery.adapter.in.web.response;

import com.logistic.delivery.domain.DeliveryStatus;
import java.util.List;
import lombok.Builder;

@Builder
public record DeliveryResponse(
  Long deliveryId,
  Long orderId,
  DeliveryStatus status,
  Long departHubId,
  Long arrivalHubId,
  String driverId,
  List<HubDeliveryHistoryResponse> hubDeliveryHistories
) {
}