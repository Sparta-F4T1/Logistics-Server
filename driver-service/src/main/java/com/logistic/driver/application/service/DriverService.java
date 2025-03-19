package com.logistic.driver.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.common.passport.model.Passport;
import com.logistic.driver.application.port.in.DriverUseCase;
import com.logistic.driver.application.port.in.command.CreateDriverCommand;
import com.logistic.driver.application.port.in.command.DeleteDriverCommand;
import com.logistic.driver.application.port.in.command.UpdateDriverCommand;
import com.logistic.driver.application.port.out.DriverInternalPort;
import com.logistic.driver.application.port.out.DriverPersistencePort;
import com.logistic.driver.application.service.dto.HubInfo;
import com.logistic.driver.domain.Driver;
import com.logistic.driver.domain.command.DriverForCreate;
import com.logistic.driver.domain.command.DriverForUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class DriverService implements DriverUseCase {
  private final DriverPersistencePort persistencePort;
  private final DriverInternalPort internalPort;

  @Override
  public Driver createDriver(final CreateDriverCommand command) {
    final HubInfo hubInfo = findHub(command.departHubId(), command.passport());
    final DriverForCreate forCreate = new DriverForCreate(command.driverId(), command.type(), hubInfo.hubId(),
        command.arrivalHubId());
    final Driver driver = Driver.create(forCreate);
    return persistencePort.save(driver);
  }

  @Override
  public Driver updateDriver(final UpdateDriverCommand command) {
    final HubInfo hubInfo = findHub(command.departHubId(), command.passport());
    Driver driver = findDriver(command.driverId());
    final DriverForUpdate forUpdate = new DriverForUpdate(command.type(), hubInfo.hubId(), command.arrivalHubId());
    driver.update(forUpdate);
    return persistencePort.save(driver);
  }

  @Override
  public void deleteDriver(final DeleteDriverCommand command) {
    final Driver driver = findDriver(command.driverId());
    driver.delete();
    persistencePort.save(driver);
  }

  private Driver findDriver(final String driverId) {
    return persistencePort.findById(driverId);
  }

  private HubInfo findHub(final Long hubId, final Passport passport) {
    return internalPort.findHub(hubId, passport);
  }
}
