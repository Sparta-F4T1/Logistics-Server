package com.logistic.hub.adapter.in.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.response.RouteClientResponse;
import com.logistic.hub.adapter.in.internal.mapper.RouteInternalMapper;
import com.logistic.hub.application.port.in.RouteUseCase;
import com.logistic.hub.application.port.in.command.DepartArrivalIdCommand;
import com.logistic.hub.domain.Route;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/hubRoutes")
public class RouteClientAdaptor {
  private final RouteUseCase routeUseCase;
  private final RouteInternalMapper routeInternalMapper;

  @GetMapping("/route")
  public List<RouteClientResponse>//공통모듈 추가 시 HubClientResponse로 변경 예정
  getRoute(@RequestParam Long departHubId, @RequestParam Long arrivalHubId) {
    DepartArrivalIdCommand command = new DepartArrivalIdCommand(departHubId, arrivalHubId);

    List<Route> response = routeUseCase.getShortestPath(command);

    return response.stream().map(routeInternalMapper::toRouteClientResponse).toList();
  }
}
