package com.logistic.delivery.adapter.in.web.request;

import java.util.Optional;
import lombok.Builder;

@Builder
public record DeliverySearchRequest(
    Optional<Long> orderId,
    Optional<Long> departCompanyId,
    Optional<Long> arrivalCompanyId,
    Optional<String> driverId
){
  public DeliverySearchRequest(
      Long orderId,
      Long departCompanyId,
      Long arrivalCompanyId,
      String driverId
  ){
    this(Optional.ofNullable(orderId),
        Optional.ofNullable(departCompanyId),
        Optional.ofNullable(arrivalCompanyId),
        Optional.ofNullable(driverId)
    );
  }
}
