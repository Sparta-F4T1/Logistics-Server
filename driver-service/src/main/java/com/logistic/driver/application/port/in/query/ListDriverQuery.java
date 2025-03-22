package com.logistic.driver.application.port.in.query;

import com.logistic.common.passport.model.Passport;
import java.util.List;

public record ListDriverQuery(
    List<String> driverIds,
    Passport passport) {
}
