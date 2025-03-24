package com.logistic.hub.application.port.in;

import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.application.port.in.command.RouteDeleteByHubIdCommand;
import com.logistic.hub.application.port.in.command.RouteDeleteCommand;
import com.logistic.hub.domain.Route;

public interface RouteUseCase {
  Route createOrUpdateHubRoute(RouteCreateCommand command);

  void deleteHubRoute(RouteDeleteCommand command);

  void deleteHubRouteByHubId(RouteDeleteByHubIdCommand command);
}
