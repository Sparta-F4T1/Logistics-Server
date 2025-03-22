package com.logistic.delivery.adapter.in.web.response;

import com.logistic.delivery.domain.DeliveryStatus;
import com.logistic.delivery.domain.vo.Distance;
import com.logistic.delivery.domain.vo.Sequence;
import com.logistic.delivery.domain.vo.Time;
import lombok.Builder;

@Builder
public record HubDeliveryHistoryResponse(
  Sequence sequence,
  Long departHubId,
  Long arrivalHubId,
  Time time,
  Distance distance,
  DeliveryStatus status,
  String driverId
) {
}