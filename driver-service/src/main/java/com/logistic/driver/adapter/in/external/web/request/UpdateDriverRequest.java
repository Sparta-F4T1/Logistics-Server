package com.logistic.driver.adapter.in.external.web.request;


import com.logistic.driver.domain.DriverType;

public record UpdateDriverRequest(
    DriverType type,
    Long departHubId,
    Long arrivalHubId) {
}
