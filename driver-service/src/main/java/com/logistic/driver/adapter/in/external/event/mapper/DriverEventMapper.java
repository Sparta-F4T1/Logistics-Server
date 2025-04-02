package com.logistic.driver.adapter.in.external.event.mapper;

import com.logistic.driver.application.port.in.command.AssignCompanyDriversCommand;
import com.logistic.driver.domain.event.CompanyDeliveryEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverEventMapper {
  AssignCompanyDriversCommand toCommand(CompanyDeliveryEvent event);
}
