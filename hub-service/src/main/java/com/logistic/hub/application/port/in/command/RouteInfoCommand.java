package com.logistic.hub.application.port.in.command;

import jakarta.validation.constraints.NotNull;

public record RouteInfoCommand(
    @NotNull Integer distance,
    @NotNull Integer duration
) {

}
