package com.logistic.driver.adapter.in.internal.mapper;

import com.logistic.common.internal.request.DriverClientRequest;
import com.logistic.common.internal.response.DriverClientResponse;
import com.logistic.common.passport.model.Passport;
import com.logistic.driver.application.port.in.command.AssignCompanyDriversCommand;
import com.logistic.driver.application.port.in.command.GetHubDriverCommand;
import com.logistic.driver.application.port.in.query.FindDriverQuery;
import com.logistic.driver.application.port.in.query.ListDriverQuery;
import com.logistic.driver.domain.model.Driver;
import com.logistic.driver.domain.model.DriverView;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverServiceMapper {
  FindDriverQuery toFindDriverQuery(String driverId, Passport passport);

  ListDriverQuery toListDriverQuery(DriverClientRequest request, Passport passport);

  DriverClientResponse toResponse(DriverView driverView);

  List<DriverClientResponse> toResponseList(List<DriverView> driverViews);

  DriverClientResponse toResponse(Driver driver);

  GetHubDriverCommand toGetHubDriverCommand(DriverClientRequest request, Passport passport);

  AssignCompanyDriversCommand toAssignCompanyDriversCommand(DriverClientRequest request, Passport passport);
}
