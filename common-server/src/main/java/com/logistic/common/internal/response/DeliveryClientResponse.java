package com.logistic.common.internal.response;

import java.util.List;

public record DeliveryClientResponse(
    Long deliveryId,
    Long orderId,
    String status,
    Long departCompanyId,
    Long arrivalCompanyId,
    Long departHubId,
    Long arrivalHubId,
    String driverId,
    List<HubDeliveryHistory> hubHistories
) {
  public record HubDeliveryHistory(
      Sequence sequence,
      Long departHubId,
      Long arrivalHubId,
      Time time,
      Distance distance,
      String status,
      String driverId
  ) {
    public record Time(
        Integer expected,
        Integer actual
    ) {

    }

    public record Distance(
        Double expected,
        Double actual
    ) {

    }

    public record Sequence(
        Integer current,
        Integer end
    ) {

    }
  }
}