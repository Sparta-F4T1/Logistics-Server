package com.logistic.delivery.domain.vo.dto;

public record HubRouteInfo(
    Long routeId,
    Long departHubId,
    Long arrivalHubId,
    Integer distance,
    Integer duration) {
}