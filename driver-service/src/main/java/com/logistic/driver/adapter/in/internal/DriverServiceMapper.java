package com.logistic.driver.adapter.in.internal;

import com.logistic.common.internal.request.DriverClientRequest;
import com.logistic.common.internal.response.DriverClientResponse;
import com.logistic.driver.application.port.in.query.FindDriverQuery;
import com.logistic.driver.application.port.in.query.ListDriverQuery;
import com.logistic.driver.domain.Driver;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DriverServiceMapper {
  @Mapping(source = "request.passport", target = "passport")
  FindDriverQuery toFindDriverQuery(String driverId, DriverClientRequest request);

  @Mapping(source = "request.passport", target = "passport")
  ListDriverQuery toListDriverQuery(DriverClientRequest request);

  @Mapping(source = "id", target = "driverId")
  DriverClientResponse toResponse(Driver driver);

  @Mapping(source = "id", target = "driverId")
  List<DriverClientResponse> toResponseList(List<Driver> drivers);
}
