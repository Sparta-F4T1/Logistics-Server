package com.logistic.common.internal.response;

public record RouteClientResponse(
    Long routeId,
    Long departHubId,
    Long arrivalHubId,
    Integer distance,
    Integer duration) {
}
