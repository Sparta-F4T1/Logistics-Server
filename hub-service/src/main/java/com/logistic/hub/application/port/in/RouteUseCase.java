package com.logistic.hub.application.port.in;

import com.logistic.hub.adapter.in.internal.response.HubClientShortestPathResponse;
import com.logistic.hub.adapter.in.web.response.RouteDetailsResponse;
import com.logistic.hub.adapter.in.web.response.RouteHistoryListResponse;
import com.logistic.hub.application.port.in.command.DepartArrivalIdCommand;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.domain.Route;

public interface RouteUseCase {
  Route createOrUpdateHubRoute(RouteCreateCommand command);

  void deleteHubRoute(Long hubRouteId);

  RouteHistoryListResponse getHubRouteList(int page, int size, String searchType, String search);

  RouteDetailsResponse getRouteDetails(Long hubRouteId);

  HubClientShortestPathResponse getShortestPath(DepartArrivalIdCommand command);
}
