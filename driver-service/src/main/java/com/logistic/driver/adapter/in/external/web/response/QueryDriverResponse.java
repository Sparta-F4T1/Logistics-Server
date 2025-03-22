package com.logistic.driver.adapter.in.external.web.response;

import com.logistic.driver.domain.model.vo.DriverStatus;
import com.logistic.driver.domain.model.vo.DriverType;

public record QueryDriverResponse(
    String driverId,
    String driverName,
    DriverType type,
    DriverStatus status,
    Long hubId,
    String hubName) {
}
