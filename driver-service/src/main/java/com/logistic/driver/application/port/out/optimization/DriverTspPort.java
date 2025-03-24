package com.logistic.driver.application.port.out.optimization;

import com.logistic.driver.domain.model.vo.Company;
import com.logistic.driver.domain.model.vo.Hub;
import java.util.List;

public interface DriverTspPort {
  List<Company> tryTsp(List<Company> companyList, Hub hub);
}
