package com.logistic.hub.application.port.in;

import com.logistic.hub.application.port.in.query.RouteFindQuery;
import com.logistic.hub.application.port.in.query.RouteSearchQuery;
import com.logistic.hub.application.service.dto.RouteDetailsDto;
import com.logistic.hub.application.service.dto.RouteHistoryDto;
import org.springframework.data.domain.Page;

public interface RouteQueryUseCase {

  Page<RouteHistoryDto> getHubRouteList(RouteSearchQuery routeSearchQuery);

  RouteDetailsDto getRouteDetails(RouteFindQuery hubRouteId);
}
