package com.logistic.driver.application.port.in.query;

import com.logistic.common.passport.model.Passport;

public record FindDriverQuery(
    String driverId,
    Passport passport) {
}
