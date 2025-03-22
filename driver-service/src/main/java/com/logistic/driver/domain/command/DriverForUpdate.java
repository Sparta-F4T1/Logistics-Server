package com.logistic.driver.domain.command;

import com.logistic.driver.domain.model.vo.DriverStatus;
import com.logistic.driver.domain.model.vo.DriverType;
import com.logistic.driver.domain.model.vo.Hub;

public record DriverForUpdate(
    DriverType type,
    DriverStatus status,
    Hub departHub) {
}
