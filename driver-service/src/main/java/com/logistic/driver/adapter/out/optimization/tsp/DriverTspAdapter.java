package com.logistic.driver.adapter.out.optimization.tsp;

import com.logistic.common.annotation.Adapter;
import com.logistic.driver.application.port.out.optimization.DriverTspPort;
import com.logistic.driver.domain.model.vo.Company;
import com.logistic.driver.domain.model.vo.Hub;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class DriverTspAdapter implements DriverTspPort {
  private final GreedyTspService greedyTspService;

  @Override
  public List<Company> tryTsp(final List<Company> companyList, final Hub hub) {
    return greedyTspService.tryTsp(companyList, hub);
  }
}
