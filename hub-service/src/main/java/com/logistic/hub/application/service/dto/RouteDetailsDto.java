package com.logistic.hub.application.service.dto;

import com.logistic.hub.domain.Route;

public record RouteDetailsDto(
    Long hubRouteId,
    Long departHubId,
    String departHubName,
    Long arrivalHubId,
    String arrivalHubName,
    Integer distance,
    Integer duration
) {

  public static RouteDetailsDto from(Route route, String departHubName, String arrivalHubName) {
    return new RouteDetailsDto(
        route.getId(),
        route.getDepartHubId(),
        departHubName,
        route.getArrivalHubId(),
        arrivalHubName,
        route.getDistance(),
        route.getDuration()
    );
  }
}
