package com.logistic.delivery.adapter.in.web.request;

import java.util.Optional;
import lombok.Builder;

@Builder
public record DeliveryUpdateRequest(
    Optional<String> status,
    Optional<String> driverId
){

  public DeliveryUpdateRequest(
      String status,
      String driverId
  ){
    this(Optional.ofNullable(status),Optional.ofNullable(driverId));
  }
}
