package com.logistic.hub.domain.command;

import jakarta.validation.constraints.NotNull;

public record RouteInfoCommand(
    @NotNull Integer distance,
    @NotNull Integer duration
) {

}
