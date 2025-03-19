package com.logistic.driver.application.port.in;

import com.logistic.driver.application.port.in.query.FindDriverQuery;
import com.logistic.driver.application.port.in.query.ListDriverQuery;
import com.logistic.driver.application.port.in.query.SearchDriverQuery;
import com.logistic.driver.domain.Driver;
import java.util.List;
import org.springframework.data.domain.Page;

public interface DriverQueryUseCase {
  Driver findDriver(FindDriverQuery query);

  List<Driver> findDriverList(ListDriverQuery query);

  Page<Driver> search(SearchDriverQuery query);
}
