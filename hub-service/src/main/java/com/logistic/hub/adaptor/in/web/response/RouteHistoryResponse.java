package com.logistic.hub.adaptor.in.web.response;

public record RouteHistoryResponse(
    Long hubRouteId,
    String departHubName,
    String arrivalHubName
) {
}
