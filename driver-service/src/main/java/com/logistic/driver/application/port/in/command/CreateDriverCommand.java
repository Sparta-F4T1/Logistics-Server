package com.logistic.driver.application.port.in.command;

import com.logistic.common.passport.model.Passport;
import com.logistic.driver.domain.command.DriverForCreate;
import com.logistic.driver.domain.model.vo.DriverStatus;
import com.logistic.driver.domain.model.vo.DriverType;
import com.logistic.driver.domain.model.vo.Hub;
import com.logistic.driver.domain.model.vo.User;

public record CreateDriverCommand(
    String driverId,
    DriverType type,
    DriverStatus status,
    Long hubId,
    Passport passport) {

  public DriverForCreate toForCreate(User user, Hub hub) {
    return new DriverForCreate(
        user, type, status, hub);
  }
}
