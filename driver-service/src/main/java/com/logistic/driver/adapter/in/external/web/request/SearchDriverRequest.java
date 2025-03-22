package com.logistic.driver.adapter.in.external.web.request;


import com.logistic.driver.domain.model.vo.DriverStatus;
import com.logistic.driver.domain.model.vo.DriverType;

public record SearchDriverRequest(
    DriverType type,
    DriverStatus status,
    Long hubId) {
}
