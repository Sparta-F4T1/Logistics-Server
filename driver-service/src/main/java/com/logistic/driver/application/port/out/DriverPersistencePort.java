package com.logistic.driver.application.port.out;

import com.logistic.driver.application.port.in.query.SearchDriverQuery;
import com.logistic.driver.domain.Driver;
import java.util.List;
import org.springframework.data.domain.Page;

public interface DriverPersistencePort {
  Driver save(Driver driver);

  Driver findById(String driverId);

  List<Driver> findAll(List<String> driverId);

  Page<Driver> search(SearchDriverQuery query);
}
