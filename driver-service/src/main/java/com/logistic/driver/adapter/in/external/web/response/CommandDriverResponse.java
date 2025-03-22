package com.logistic.driver.adapter.in.external.web.response;


import com.logistic.driver.domain.model.vo.DriverStatus;
import com.logistic.driver.domain.model.vo.DriverType;

public record CommandDriverResponse(
    String driverId,
    DriverType type,
    DriverStatus status,
    Long hubId) {
}
