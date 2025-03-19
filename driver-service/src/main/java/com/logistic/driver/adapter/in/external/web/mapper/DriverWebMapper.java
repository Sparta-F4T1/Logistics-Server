package com.logistic.driver.adapter.in.external.web.mapper;

import com.logistic.common.passport.model.Passport;
import com.logistic.driver.adapter.in.external.web.request.CreateDriverRequest;
import com.logistic.driver.adapter.in.external.web.request.SearchDriverRequest;
import com.logistic.driver.adapter.in.external.web.request.UpdateDriverRequest;
import com.logistic.driver.adapter.in.external.web.response.FindDriverResponse;
import com.logistic.driver.application.port.in.command.CreateDriverCommand;
import com.logistic.driver.application.port.in.command.DeleteDriverCommand;
import com.logistic.driver.application.port.in.command.UpdateDriverCommand;
import com.logistic.driver.application.port.in.query.FindDriverQuery;
import com.logistic.driver.application.port.in.query.SearchDriverQuery;
import com.logistic.driver.domain.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Pageable;

@Mapper(componentModel = "spring")
public interface DriverWebMapper {
  CreateDriverCommand toCreateCommand(CreateDriverRequest request, Passport passport);

  UpdateDriverCommand toUpdateCommand(String driverId, UpdateDriverRequest request, Passport passport);

  DeleteDriverCommand toDeleteCommand(String driverId, Passport passport);

  @Mapping(source = "id", target = "driverId")
  FindDriverResponse toResponse(Driver driver);

  FindDriverQuery toFindQuery(String driverId, Passport passport);

  SearchDriverQuery toSearchQuery(SearchDriverRequest request, Pageable pageable, Passport passport);
}
