package com.logistic.driver.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.driver.application.port.in.DriverCommandUseCase;
import com.logistic.driver.application.port.in.command.CreateDriverCommand;
import com.logistic.driver.application.port.in.command.DeleteDriverCommand;
import com.logistic.driver.application.port.in.command.UpdateDriverCommand;
import com.logistic.driver.application.port.out.DriverCommandPersistencePort;
import com.logistic.driver.application.port.out.DriverInternalPort;
import com.logistic.driver.domain.DriverPolicyService;
import com.logistic.driver.domain.command.DriverForCreate;
import com.logistic.driver.domain.command.DriverForUpdate;
import com.logistic.driver.domain.model.Driver;
import com.logistic.driver.domain.model.vo.Hub;
import com.logistic.driver.domain.model.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class DriverCommandService implements DriverCommandUseCase {
  private final DriverInternalPort internalPort;
  private final DriverPolicyService policyService;
  private final DriverCommandPersistencePort persistencePort;

  @Override
  public Driver createDriver(final CreateDriverCommand command) {
    final User user = findUser(command.driverId());
    final Hub hub = findHub(command.hubId());
    policyService.validateCreateDriver(command.passport(), hub);
    final DriverForCreate forCreate = command.toForCreate(user, hub);
    final Driver driver = Driver.create(forCreate);
    return persistencePort.save(driver);
  }

  @Override
  public Driver updateDriver(final UpdateDriverCommand command) {
    Driver driver = findDriver(command.driverId());
    final Hub hub = findHub(command.hubId());
    policyService.validateUpdateDriver(command.passport(), hub);
    final DriverForUpdate forUpdate = command.toForUpdate(hub);
    driver.update(forUpdate);
    return persistencePort.save(driver);
  }

  @Override
  public void deleteDriver(final DeleteDriverCommand command) {
    Driver driver = findDriver(command.driverId());
    final Hub hub = findHub(driver.getHubId());
    policyService.validateDeleteDriver(command.passport(), hub);
    driver.delete();
    persistencePort.save(driver);
  }

  private Driver findDriver(final String driverId) {
    return persistencePort.findById(driverId);
  }

  private Hub findHub(final Long hubId) {
    return internalPort.findHub(hubId);
  }

  private User findUser(final String userId) {
    return internalPort.findUser(userId);
  }
}
