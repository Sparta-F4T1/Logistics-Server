package com.logistic.driver.adapter.in.external.web.response;


import com.logistic.driver.domain.DriverType;

public record FindDriverResponse(
    String driverId,
    DriverType type,
    Long departHubId,
    Long arrivalHubId) {
}
