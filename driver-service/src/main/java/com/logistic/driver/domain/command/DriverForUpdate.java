package com.logistic.driver.domain.command;

import com.logistic.driver.domain.DriverType;

public record DriverForUpdate(
    DriverType type,
    Long departHubId,
    Long arrivalHubId) {
}
