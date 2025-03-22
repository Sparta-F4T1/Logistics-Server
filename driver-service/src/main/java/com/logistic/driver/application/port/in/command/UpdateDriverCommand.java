package com.logistic.driver.application.port.in.command;

import com.logistic.common.passport.model.Passport;
import com.logistic.driver.domain.command.DriverForUpdate;
import com.logistic.driver.domain.model.vo.DriverStatus;
import com.logistic.driver.domain.model.vo.DriverType;
import com.logistic.driver.domain.model.vo.Hub;

public record UpdateDriverCommand(
    String driverId,
    DriverType type,
    DriverStatus status,
    Long hubId,
    Passport passport) {
  public DriverForUpdate toForUpdate(Hub hub) {
    return new DriverForUpdate(
        type, status, hub);
  }
}
