package com.logistic.hub.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.hub.adaptor.in.web.response.RouteDetailsResponse;
import com.logistic.hub.adaptor.in.web.response.RouteHistoryListResponse;
import com.logistic.hub.adaptor.in.web.response.RouteHistoryResponse;
import com.logistic.hub.application.port.in.HubUseCase;
import com.logistic.hub.application.port.in.RouteUseCase;
import com.logistic.hub.application.port.in.command.DepartArrivalCommand;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.application.port.in.command.RouteInfoCommand;
import com.logistic.hub.application.port.out.NaverClientPort;
import com.logistic.hub.application.port.out.RoutePersistencePort;
import com.logistic.hub.domain.Route;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RouteService implements RouteUseCase {
  private final RoutePersistencePort routePersistencePort;
  private final HubUseCase hubUseCase;
  private final NaverClientPort naverClientPort;

  @Override
  public Route createHubRoute(RouteCreateCommand routeCommand) {
    RouteInfoCommand routeInfoCommand = naverClientPort.getRouteInfo(routeCommand); //임시
    Long departHubId = routeCommand.departHubId();
    Long arrivalHubId = routeCommand.arrivalHubId();
    if (departHubId.equals(arrivalHubId)) {
      throw new IllegalArgumentException("출발 허브와 도착 허브는 같을 수 없습니다");
    }
    if (!hubUseCase.existsHub(departHubId) || !hubUseCase.existsHub(departHubId)) {
      throw new IllegalArgumentException("출발 혹은 도착 허브가 존재하지 않습니다");
    }
    Route route = Route.createRoute(routeCommand, routeInfoCommand);

    return routePersistencePort.save(route).orElseThrow(() -> new IllegalArgumentException("경로 생성 실패"));
  }

  @Override
  public RouteHistoryListResponse getHubRouteList(int page, int size, String orderBy, String search) {
    Sort.Direction direction = Direction.ASC;  // 오름차순
    Sort sort1 = Sort.by(direction, orderBy); //정렬기준
    Pageable pageable = PageRequest.of(page, size, sort1);

    Page<RouteHistoryResponse> list = routePersistencePort.findAllBySearch(search, pageable);
    RouteHistoryListResponse routeList = RouteHistoryListResponse.from(list);
    return routeList;
  }

  @Override
  public RouteDetailsResponse getRouteDetails(Long hubRouteId) {
    Route route = routePersistencePort.findById(hubRouteId)
        .orElseThrow(() -> new IllegalArgumentException("경로가 존재하지 않습니다"));

    isDeleted(route);
    DepartArrivalCommand command = hubUseCase.getHubNameInfo(route.getDepartHubId(), route.getArrivalHubId());
    RouteDetailsResponse routeDetails = RouteDetailsResponse.from(route, command.departHubName(),
        command.arrivalHubName());
    return routeDetails;
  }


  @Override
  public void deleteHubRoute(Long hubRouteId) {
    Route route = routePersistencePort.findById(hubRouteId)
        .orElseThrow(() -> new IllegalArgumentException("경로가 존재하지 않습니다"));
    isDeleted(route);
    routePersistencePort.delete(route);
  }

  private void isDeleted(Route route) {
    if (route.getIsDeleted()) {
      throw new IllegalArgumentException("이미 삭제된 허브입니다.");
    }
  }
}
