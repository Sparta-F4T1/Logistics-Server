package com.logistic.driver.application.port.in.query;

import com.logistic.common.passport.model.Passport;
import com.logistic.driver.domain.DriverType;
import org.springframework.data.domain.Pageable;

public record SearchDriverQuery(
    Long departHubId,
    Long arrivalHubId,
    DriverType type,
    Pageable pageable,
    Passport passport) {
}
