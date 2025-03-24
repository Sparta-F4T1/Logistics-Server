package com.logistic.delivery.adapter.out.client.mapper;

import com.logistic.common.internal.response.DriverClientResponse;
import com.logistic.common.internal.response.RouteClientResponse;
import com.logistic.delivery.domain.vo.dto.HubDriverInfo;
import com.logistic.delivery.domain.vo.dto.HubRouteInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryClientMapper {
  HubRouteInfo toHubRoute(RouteClientResponse response);
  HubDriverInfo toHubDriver(DriverClientResponse response);
}
