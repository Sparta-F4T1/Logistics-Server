package com.logistic.hub.adapter.in.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.response.RouteClientResponse;
import com.logistic.hub.adapter.in.internal.mapper.RouteInternalMapper;
import com.logistic.hub.application.port.in.RouteQueryUseCase;
import com.logistic.hub.application.port.in.command.DepartArrivalIdCommand;
import com.logistic.hub.application.port.in.query.RouteFindQuery;
import com.logistic.hub.domain.Route;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/routes")
public class RouteClientAdaptor {
  private final RouteQueryUseCase queryUseCase;
  private final RouteInternalMapper routeInternalMapper;


  @GetMapping("/{routeId}")
  public RouteClientResponse findRoute(@PathVariable("routeId") Long routeId) {
    Route route = queryUseCase.findRoute(new RouteFindQuery(routeId));

    return routeInternalMapper.toRouteClientResponse(route);
  }

  @GetMapping("/shortestPath")
  public List<RouteClientResponse>
  shortestPath(@RequestParam Long departHubId, @RequestParam Long arrivalHubId) {
    DepartArrivalIdCommand command = new DepartArrivalIdCommand(departHubId, arrivalHubId);

    List<Route> response = queryUseCase.getShortestPath(command);

    return response.stream().map(routeInternalMapper::toRouteClientResponse).toList();
  }
}
