package com.logistic.hub.adaptor.in.web.response;

import com.logistic.hub.domain.Route;

public record RouteDetailsResponse(
    Long hubRouteId,
    Long departHubId,
    String departHubName,
    Long arrivalHubId,
    String arrivalHubName,
    Integer distance,
    Integer duration
) {
  public static RouteDetailsResponse from(Route route, String departHubName, String arrivalHubName) {
    return new RouteDetailsResponse(
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
