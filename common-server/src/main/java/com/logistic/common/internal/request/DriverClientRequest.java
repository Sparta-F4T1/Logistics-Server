package com.logistic.common.internal.request;

import java.util.List;

public record DriverClientRequest(
    List<hubRoute> hubRouteList) {
  public record hubRoute(
      Long departHubId,
      Long arrivalHubId) {
  }
}
