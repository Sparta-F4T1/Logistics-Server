package com.logistic.hub.application.service.dto;


public record RouteHistoryDto(
    Long hubRouteId,
    String departHubName,
    String arrivalHubName
) {
}
