package com.logistic.driver.domain.command;

import com.logistic.driver.domain.DriverType;

public record DriverForCreate(
    String driverId,
    DriverType type,
    Long departHubId,
    Long arrivalHubId) {
}
