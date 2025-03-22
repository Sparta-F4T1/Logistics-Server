package com.logistic.driver.domain.command;

import com.logistic.driver.domain.model.vo.DriverStatus;
import com.logistic.driver.domain.model.vo.DriverType;
import com.logistic.driver.domain.model.vo.Hub;
import com.logistic.driver.domain.model.vo.User;

public record DriverViewForCreate(
    User user,
    DriverType type,
    DriverStatus status,
    Hub hub) {
}
