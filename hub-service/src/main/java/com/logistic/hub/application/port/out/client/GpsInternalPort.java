package com.logistic.hub.application.port.out.client;

import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.application.port.in.command.RouteInfoCommand;
import com.logistic.hub.domain.command.AddressCommand;

public interface GpsInternalPort {
  AddressCommand getAddressCommand(String roadAddress, String jibunAddress);

  RouteInfoCommand getRouteInfo(RouteCreateCommand routeCommand);
}
