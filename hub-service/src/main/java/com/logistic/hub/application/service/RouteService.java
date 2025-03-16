package com.logistic.hub.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.hub.application.port.in.RouteUseCase;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.application.port.out.HubPersistencePort;
import com.logistic.hub.application.port.out.NaverClientPort;
import com.logistic.hub.application.port.out.RoutePersistencePort;
import com.logistic.hub.domain.Route;
import com.logistic.hub.domain.command.RouteInfoCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RouteService implements RouteUseCase {
  private final RoutePersistencePort routePersistencePort;
  private final HubPersistencePort hubPersistencePort; //검증에 사용
  private final NaverClientPort naverClientPort;

  @Override
  public Route createHubRoute(RouteCreateCommand routeCommand) {
    RouteInfoCommand routeInfoCommand = naverClientPort.getRouteInfo(routeCommand); //임시
    Long departHubId = routeCommand.departHubId();
    Long arrivalHubId = routeCommand.arrivalHubId();
    if (departHubId.equals(arrivalHubId)) {
      throw new IllegalArgumentException("출발 허브와 도착 허브는 같을 수 없습니다");
    }
    if (hubPersistencePort.findById(departHubId).isEmpty() || hubPersistencePort.findById(arrivalHubId).isEmpty()) {
      throw new IllegalArgumentException("출발 혹은 도착 허브가 존재하지 않습니다");
    }
    Route route = Route.createRoute(routeCommand, routeInfoCommand);

    return routePersistencePort.save(route).orElseThrow(() -> new IllegalArgumentException("경로 생성 실패"));
  }
}
