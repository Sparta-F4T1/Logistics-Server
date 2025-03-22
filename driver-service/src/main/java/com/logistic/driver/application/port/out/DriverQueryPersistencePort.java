package com.logistic.driver.application.port.out;

import com.logistic.driver.application.port.in.query.SearchDriverQuery;
import com.logistic.driver.domain.model.DriverView;
import java.util.List;
import org.springframework.data.domain.Page;

public interface DriverQueryPersistencePort {
  DriverView save(DriverView driverView);

  DriverView findById(String driverId);

  List<DriverView> findAll(List<String> driverId);

  Page<DriverView> search(SearchDriverQuery query);
}
