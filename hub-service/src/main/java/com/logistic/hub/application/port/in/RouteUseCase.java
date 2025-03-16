package com.logistic.hub.application.port.in;

import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.domain.Route;

public interface RouteUseCase {
  Route createHubRoute(RouteCreateCommand command);

  void deleteHubRoute(Long hubRouteId);
}
