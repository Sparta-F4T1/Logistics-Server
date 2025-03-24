package com.logistic.driver.adapter.out.optimization.clustering;

import com.logistic.common.annotation.Adapter;
import com.logistic.driver.application.port.out.optimization.DriverClusteringPort;
import com.logistic.driver.domain.model.Driver;
import com.logistic.driver.domain.model.vo.Company;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class DriverClusteringAdapter implements DriverClusteringPort {
  private final KMeansClusteringService kMeansClusteringService;

  @Override
  public Map<String, List<Company>> createClustering(final List<Company> companyList, final List<Driver> driverList) {
    return kMeansClusteringService.createClustering(companyList, driverList);
  }

}
