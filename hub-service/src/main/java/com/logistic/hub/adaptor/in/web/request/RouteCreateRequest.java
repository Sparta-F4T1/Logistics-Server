package com.logistic.hub.adaptor.in.web.request;

import jakarta.validation.constraints.NotNull;

public record RouteCreateRequest(
    @NotNull Long departHubId,
    @NotNull Long arrivalHubId
) {
}
