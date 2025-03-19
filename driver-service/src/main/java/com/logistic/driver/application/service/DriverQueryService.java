package com.logistic.driver.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.driver.application.port.in.DriverQueryUseCase;
import com.logistic.driver.application.port.in.query.FindDriverQuery;
import com.logistic.driver.application.port.in.query.ListDriverQuery;
import com.logistic.driver.application.port.in.query.SearchDriverQuery;
import com.logistic.driver.application.port.out.DriverPersistencePort;
import com.logistic.driver.domain.Driver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DriverQueryService implements DriverQueryUseCase {
  private final DriverPersistencePort driverPersistencePort;

  @Override
  public Driver findDriver(final FindDriverQuery query) {
    return driverPersistencePort.findById(query.driverId());
  }

  @Override
  public List<Driver> findDriverList(final ListDriverQuery query) {
    return driverPersistencePort.findAll(query.driverIds());
  }

  @Override
  public Page<Driver> search(final SearchDriverQuery query) {
    return driverPersistencePort.search(query);
  }
}
