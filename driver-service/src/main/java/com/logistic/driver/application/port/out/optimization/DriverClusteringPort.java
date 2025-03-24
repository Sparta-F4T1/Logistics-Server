package com.logistic.driver.application.port.out.optimization;

import com.logistic.driver.domain.model.Driver;
import com.logistic.driver.domain.model.vo.Company;
import java.util.List;
import java.util.Map;

public interface DriverClusteringPort {
  Map<String, List<Company>> createClustering(List<Company> companyList, List<Driver> driverList);
}
