package com.logistic.hub.application.port.in.command;

import jakarta.validation.constraints.NotNull;

public record HubCreateCommand(
    @NotNull String hubType,
    @NotNull String hubName,
    @NotNull String roadAddress,
    @NotNull String jibunAddress
) {
}
