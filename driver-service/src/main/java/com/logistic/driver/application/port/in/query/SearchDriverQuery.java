package com.logistic.driver.application.port.in.query;

import com.logistic.common.passport.model.Passport;
import com.logistic.driver.domain.model.vo.DriverStatus;
import com.logistic.driver.domain.model.vo.DriverType;
import org.springframework.data.domain.Pageable;

public record SearchDriverQuery(
    Long hubId,
    DriverType type,
    DriverStatus status,
    Pageable pageable,
    Passport passport) {
}
