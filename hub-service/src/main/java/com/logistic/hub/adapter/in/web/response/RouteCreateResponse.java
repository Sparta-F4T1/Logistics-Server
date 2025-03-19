package com.logistic.hub.adapter.in.web.response;

public record RouteCreateResponse(
    Long hubRouteId,
    Double distance,
    Double duration
) {

}
