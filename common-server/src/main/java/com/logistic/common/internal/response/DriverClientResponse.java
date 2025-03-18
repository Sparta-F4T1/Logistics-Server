package com.logistic.common.internal.response;

public record DriverClientResponse(
    String driverId,
    String type,
    Long departHubId,
    Long arrivalHubId) {
}
