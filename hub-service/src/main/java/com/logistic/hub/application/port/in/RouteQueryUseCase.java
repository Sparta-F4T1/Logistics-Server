package com.logistic.hub.application.port.in;

import com.logistic.hub.application.port.in.command.DepartArrivalIdCommand;
import com.logistic.hub.application.port.in.query.RouteFindQuery;
import com.logistic.hub.application.port.in.query.RouteListQuery;
import com.logistic.hub.application.port.in.query.RouteSearchQuery;
import com.logistic.hub.application.service.dto.RouteDetailsDto;
import com.logistic.hub.application.service.dto.RouteHistoryDto;
import com.logistic.hub.domain.Route;
import java.util.List;
import org.springframework.data.domain.Page;

public interface RouteQueryUseCase {

  Page<RouteHistoryDto> getHubRouteList(RouteSearchQuery routeSearchQuery);

  List<Route> getShortestPath(DepartArrivalIdCommand command);

  RouteDetailsDto getRouteDetails(RouteFindQuery routeFindQuery);

  Route findRoute(RouteFindQuery routeFindQuery);

  List<Route> findRouteList(RouteListQuery routeListQuery);
}
