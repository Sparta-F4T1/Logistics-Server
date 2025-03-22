package com.logistic.driver.application.port.in;

import com.logistic.driver.application.port.in.query.FindDriverQuery;
import com.logistic.driver.application.port.in.query.ListDriverQuery;
import com.logistic.driver.application.port.in.query.SearchDriverQuery;
import com.logistic.driver.domain.model.DriverView;
import java.util.List;
import org.springframework.data.domain.Page;

public interface DriverQueryUseCase {
  DriverView findDriver(FindDriverQuery query);

  List<DriverView> findDriverList(ListDriverQuery query);

  Page<DriverView> search(SearchDriverQuery query);
}
