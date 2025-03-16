package com.logistic.hub.adaptor.in.web.response;

public record RouteCreateResponse(
    Long hubRouteId,
    Double distance,
    Double duration
) {

}
