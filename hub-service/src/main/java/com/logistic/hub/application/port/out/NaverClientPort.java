package com.logistic.hub.application.port.out;

import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.application.port.in.command.RouteInfoCommand;
import com.logistic.hub.domain.command.AddressCommand;

public interface NaverClientPort {
  AddressCommand getAddressCommand(String roadAddress, String jibunAddress);

  RouteInfoCommand getRouteInfo(RouteCreateCommand routeCommand);
}
