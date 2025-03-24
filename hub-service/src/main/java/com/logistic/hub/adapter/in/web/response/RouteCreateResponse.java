package com.logistic.hub.adapter.in.web.response;

public record RouteCreateResponse(
    Long routeId,
    Double distance,
    Double duration
) {

}
