package com.logistic.driver.application.port.in.command;

import com.logistic.common.passport.model.Passport;

public record DeleteDriverCommand(
    String driverId,
    Passport passport) {
}
