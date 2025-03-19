package com.logistic.driver.application.port.in;

import com.logistic.driver.application.port.in.command.CreateDriverCommand;
import com.logistic.driver.application.port.in.command.DeleteDriverCommand;
import com.logistic.driver.application.port.in.command.UpdateDriverCommand;
import com.logistic.driver.domain.Driver;

public interface DriverUseCase {
  Driver createDriver(CreateDriverCommand command);

  Driver updateDriver(UpdateDriverCommand command);

  void deleteDriver(DeleteDriverCommand command);
}
