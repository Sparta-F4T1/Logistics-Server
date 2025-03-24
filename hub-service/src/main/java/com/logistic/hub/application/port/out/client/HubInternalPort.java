package com.logistic.hub.application.port.out.client;

import com.logistic.hub.application.port.in.command.RouteInfoCommand;
import com.logistic.hub.application.port.in.command.UserInfoCommand;
import com.logistic.hub.domain.command.AddressCommand;
import java.util.List;

public interface HubInternalPort {
  AddressCommand getAddressCommand(String roadAddress, String jibunAddress);

  RouteInfoCommand getRouteInfo(String departGps, String arrivalGps);

  List<UserInfoCommand> findUserList(List<String> userIds);
}
