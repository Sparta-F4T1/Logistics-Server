package com.logistic.driver.application.port.in.command;

import java.util.List;

public record AssignCompanyDriversCommand(
    List<CompanyRoute> companyRoutes) {
  public record CompanyRoute(
      Long hubId,
      List<Long> companyIds) {
  }
}
