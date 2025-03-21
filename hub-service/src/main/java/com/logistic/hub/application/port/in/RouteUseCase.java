package com.logistic.hub.application.port.in;

import com.logistic.hub.application.port.in.command.DepartArrivalIdCommand;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.domain.Route;
import java.util.List;

public interface RouteUseCase {
  Route createOrUpdateHubRoute(RouteCreateCommand command);

  void deleteHubRoute(Long hubRouteId);

  List<Route> getShortestPath(DepartArrivalIdCommand command);
}
