package com.logistic.hub.adapter.in.web.request;

import jakarta.validation.constraints.NotNull;

public record RouteCreateRequest(
    @NotNull Long departHubId,
    @NotNull Long arrivalHubId
) {
}
