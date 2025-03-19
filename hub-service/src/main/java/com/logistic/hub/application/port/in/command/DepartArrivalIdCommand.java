package com.logistic.hub.application.port.in.command;

public record DepartArrivalIdCommand(
    Long departHubId,
    Long arrivalHubId
) {
}
