package com.logistic.common.internal.request;

public record RouteClientRequest(
    Long departHubId,
    Long arrivalHubId
) {
}
