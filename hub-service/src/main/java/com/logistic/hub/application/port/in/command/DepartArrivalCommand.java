package com.logistic.hub.application.port.in.command;

public record DepartArrivalCommand(
    String departHubName,
    String arrivalHubName
) {
}
