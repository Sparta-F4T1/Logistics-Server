package com.logistic.hub.adapter.out.client.mapper;

import com.logistic.common.internal.response.GpsClientResponse;
import com.logistic.common.internal.response.UserClientResponse;
import com.logistic.hub.application.port.in.command.RouteInfoCommand;
import com.logistic.hub.application.port.in.command.UserInfoCommand;
import com.logistic.hub.domain.command.AddressCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HubClientMapper {

  AddressCommand toAddressCommand(GpsClientResponse gps);

  RouteInfoCommand toRouteInfoCommand(GpsClientResponse distanceAndDuration);

  UserInfoCommand toUserInfoCommand(UserClientResponse userClientResponse);
}
