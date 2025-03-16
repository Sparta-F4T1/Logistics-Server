package com.logistic.hub.application.port.in.command;

import jakarta.validation.constraints.NotNull;

public record RouteCreateCommand(
    @NotNull Long departHubId,
    @NotNull Long arrivalHubId
) {
}
