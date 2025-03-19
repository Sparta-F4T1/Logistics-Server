package com.logistic.hub.adapter.in.web.response;

public record RouteHistoryResponse(
    Long hubRouteId,
    String departHubName,
    String arrivalHubName
) {
}
