package com.logistic.hub.application.port.in;

import com.logistic.hub.adaptor.in.web.response.RouteDetailsResponse;
import com.logistic.hub.adaptor.in.web.response.RouteHistoryListResponse;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.domain.Route;

public interface RouteUseCase {
  Route createHubRoute(RouteCreateCommand command);

  void deleteHubRoute(Long hubRouteId);

  RouteHistoryListResponse getHubRouteList(int page, int size, String searchType, String search);

  RouteDetailsResponse getRouteDetails(Long hubRouteId);
}
