package com.logistic.driver.application.port.in.command;

import com.logistic.common.passport.model.Passport;
import com.logistic.driver.domain.DriverType;

public record CreateDriverCommand(
    String driverId,
    DriverType type,
    Long departHubId,
    Long arrivalHubId,
    Passport passport) {
}
