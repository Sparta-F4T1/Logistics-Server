package com.logistic.driver.adapter.out.internal.mapper;

import com.logistic.common.internal.response.HubClientResponse;
import com.logistic.driver.application.service.dto.HubInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverClientMapper {
  HubInfo toHubInfo(HubClientResponse hubClientResponse);
}
