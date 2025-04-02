package com.logistic.driver.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.driver.application.port.in.DriverCommandUseCase;
import com.logistic.driver.application.port.in.command.AssignCompanyDriversCommand;
import com.logistic.driver.application.port.in.command.CreateDriverCommand;
import com.logistic.driver.application.port.in.command.DeleteDriverCommand;
import com.logistic.driver.application.port.in.command.GetHubDriverCommand;
import com.logistic.driver.application.port.in.command.GetHubDriverCommand.hubRoute;
import com.logistic.driver.application.port.in.command.UpdateDriverCommand;
import com.logistic.driver.application.port.out.DriverInternalPort;
import com.logistic.driver.application.port.out.DriverMessagePort;
import com.logistic.driver.application.port.out.optimization.DriverClusteringPort;
import com.logistic.driver.application.port.out.persistence.DriverCommandPersistencePort;
import com.logistic.driver.domain.DriverPolicyService;
import com.logistic.driver.domain.command.DriverForCreate;
import com.logistic.driver.domain.command.DriverForUpdate;
import com.logistic.driver.domain.event.DriverRouteNotificationEvent;
import com.logistic.driver.domain.model.Driver;
import com.logistic.driver.domain.model.vo.Company;
import com.logistic.driver.domain.model.vo.Hub;
import com.logistic.driver.domain.model.vo.User;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class DriverCommandService implements DriverCommandUseCase {
  private final DriverMessagePort messagePort;
  private final DriverInternalPort internalPort;
  private final DriverPolicyService policyService;
  private final DriverClusteringPort clusteringPort;
  private final ApplicationEventPublisher eventPublisher;
  private final DriverCommandPersistencePort persistencePort;
  
  @Override
  public Driver createDriver(final CreateDriverCommand command) {
    final User user = findUser(command.driverId());
    final Hub departHub = findHub(command.departHubId());
    policyService.validateCreateDriver(command.passport(), departHub);
    final DriverForCreate forCreate = command.toForCreate(user, departHub);
    final Driver driver = Driver.create(forCreate);
    return persistencePort.save(driver);
  }

  @Override
  public Driver updateDriver(final UpdateDriverCommand command) {
    Driver driver = findDriver(command.driverId());
    final Hub hub = findHub(command.departHubId());
    policyService.validateUpdateDriver(command.passport(), hub);
    final DriverForUpdate forUpdate = command.toForUpdate(hub);
    driver.update(forUpdate);
    return persistencePort.save(driver);
  }

  @Override
  public void deleteDriver(final DeleteDriverCommand command) {
    Driver driver = findDriver(command.driverId());
    final Hub hub = findHub(driver.getDepartHubId());
    policyService.validateDeleteDriver(command.passport(), hub);
    driver.delete();
    persistencePort.save(driver);
  }

  @Override
  public List<Driver> getHubDriverList(final GetHubDriverCommand command) {
    final List<hubRoute> hubRoutes = command.hubRouteList();
    return hubRoutes.stream()
        .map(hubRoute -> persistencePort.getHubDriver(
            hubRoute.departHubId(), hubRoute.arrivalHubId()))
        .toList();
  }

  @Override
  public void assignCompanyDrivers(final AssignCompanyDriversCommand command) {
    log.info("[AssignCompanyDrivers] command: {}", command);
    final Long hubId = command.hubId();
    final List<Long> companyIds = command.companyIds();
    final Hub hub = findHub(hubId);
    assignCompanyDriver(hub, companyIds);
  }

  private void assignCompanyDriver(final Hub hub, final List<Long> companyIds) {
    final List<Driver> companyDrivers = findCompanyDriverList(hub.hubId());
    final List<Company> companyList = findCompanyList(companyIds);
    final Map<String, List<Company>> clustering = processClustering(companyList, companyDrivers);
    sendCompanyDriverAssignment(clustering);
    publishNotificationEvent(clustering, hub);
  }

  private Map<String, List<Company>> processClustering(final List<Company> companyList,
                                                       final List<Driver> companyDrivers) {
    return clusteringPort.createClustering(companyList, companyDrivers);
  }

  private void sendCompanyDriverAssignment(final Map<String, List<Company>> clustering) {
    messagePort.sendCompanyDriver(clustering);
  }

  private void publishNotificationEvent(final Map<String, List<Company>> clustering, final Hub hub) {
    eventPublisher.publishEvent(new DriverRouteNotificationEvent(clustering, hub));
  }

  private List<Driver> findCompanyDriverList(final Long hubId) {
    return persistencePort.getCompanyDrivers(hubId);
  }

  private Driver findDriver(final String driverId) {
    return persistencePort.findById(driverId);
  }

  private Hub findHub(final Long hubId) {
    return internalPort.findHub(hubId);
  }


  private List<Company> findCompanyList(final List<Long> companyIds) {
    return internalPort.findCompanyList(companyIds);
  }

  private User findUser(final String userId) {
    return internalPort.findUser(userId);
  }

}
