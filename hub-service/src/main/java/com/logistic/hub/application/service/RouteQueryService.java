package com.logistic.hub.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.hub.application.port.in.HubUseCase;
import com.logistic.hub.application.port.in.RouteQueryUseCase;
import com.logistic.hub.application.port.in.query.RouteFindQuery;
import com.logistic.hub.application.port.in.query.RouteSearchQuery;
import com.logistic.hub.application.port.out.persistence.RoutePersistencePort;
import com.logistic.hub.application.service.dto.DepartArrivalDto;
import com.logistic.hub.application.service.dto.RouteDetailsDto;
import com.logistic.hub.application.service.dto.RouteHistoryDto;
import com.logistic.hub.config.RestPage;
import com.logistic.hub.domain.Route;
import com.logistic.hub.domain.exception.RouteAlreadyDeletedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RouteQueryService implements RouteQueryUseCase {
  private final RoutePersistencePort routePersistencePort;
  private final HubUseCase hubUseCase;

  @Override
  @Cacheable(cacheNames = "routeList", key = "{#routeSearchQuery.page(),#routeSearchQuery.size(),#routeSearchQuery.searchType(), #routeSearchQuery.search()}")
  public RestPage<RouteHistoryDto> getHubRouteList(RouteSearchQuery routeSearchQuery) {
    Sort.Direction direction = Direction.ASC;  // 오름차순

    Sort sort1 = Sort.by(direction, "Id"); //정렬기준
    Pageable pageable = PageRequest.of(routeSearchQuery.page(), routeSearchQuery.size(), sort1);

    Page<RouteHistoryDto> list = routePersistencePort.findAllBySearch(routeSearchQuery.searchType(),
        routeSearchQuery.search(), pageable);
    return new RestPage<>(list);
  }

  @Override
  public RouteDetailsDto getRouteDetails(RouteFindQuery routeFindQuery) {
    Route route = routePersistencePort.findById(routeFindQuery.routeId());

    isDeleted(route);
    DepartArrivalDto command = hubUseCase.getHubNameInfo(route.getDepartHubId(), route.getArrivalHubId());
    RouteDetailsDto routeDetails = RouteDetailsDto.from(route, command.departHubName(),
        command.arrivalHubName());
    return routeDetails;
  }

  private void isDeleted(Route route) {
    if (route.getIsDeleted()) {
      throw new RouteAlreadyDeletedException("이미 삭제된 허브입니다.");
    }
  }
}
