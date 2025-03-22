package com.logistic.driver.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.driver.application.port.in.DriverQueryUseCase;
import com.logistic.driver.application.port.in.query.FindDriverQuery;
import com.logistic.driver.application.port.in.query.ListDriverQuery;
import com.logistic.driver.application.port.in.query.SearchDriverQuery;
import com.logistic.driver.application.port.out.DriverQueryPersistencePort;
import com.logistic.driver.domain.model.DriverView;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DriverQueryService implements DriverQueryUseCase {
  private final DriverQueryPersistencePort persistencePort;
  
  @Override
  public DriverView findDriver(final FindDriverQuery query) {
    return persistencePort.findById(query.driverId());
  }

  @Override
  public List<DriverView> findDriverList(final ListDriverQuery query) {
    return persistencePort.findAll(query.driverIds());
  }

  @Override
  public Page<DriverView> search(final SearchDriverQuery query) {
    return persistencePort.search(query);
  }
}
