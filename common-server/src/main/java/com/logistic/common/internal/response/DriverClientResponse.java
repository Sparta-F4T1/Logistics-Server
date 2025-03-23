package com.logistic.common.internal.response;

import java.util.List;

public record DriverClientResponse(
    List<hubDriver> hubDriverList) {
  public record hubDriver(
      String driverId,
      Long departHubId,
      Long arrivalHubId) {
  }
}
