package com.logistic.driver.adapter.in.external.web.request;


import com.logistic.driver.domain.DriverType;
import jakarta.validation.constraints.NotNull;

public record SearchDriverRequest(
    @NotNull DriverType type,
    @NotNull Long departHubId,
    @NotNull Long arrivalHubId) {
}
