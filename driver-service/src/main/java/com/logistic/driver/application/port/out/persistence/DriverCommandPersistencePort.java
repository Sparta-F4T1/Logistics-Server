package com.logistic.driver.application.port.out.persistence;

import com.logistic.driver.application.port.in.query.SearchDriverQuery;
import com.logistic.driver.domain.model.Driver;
import java.util.List;
import org.springframework.data.domain.Page;

public interface DriverCommandPersistencePort {
  Driver save(Driver driver);

  Driver findById(String driverId);

  List<Driver> findAll(List<String> driverId);

  Page<Driver> search(SearchDriverQuery query);

  Driver getHubDriver(Long departHubId, Long arrivalHubId);

  List<Driver> getCompanyDrivers(Long departHubId);
}
