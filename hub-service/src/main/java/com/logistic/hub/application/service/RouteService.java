package com.logistic.hub.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.hub.application.port.in.HubQueryUseCase;
import com.logistic.hub.application.port.in.RouteUseCase;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.application.port.in.command.RouteDeleteByHubIdCommand;
import com.logistic.hub.application.port.in.command.RouteDeleteCommand;
import com.logistic.hub.application.port.in.command.RouteInfoCommand;
import com.logistic.hub.application.port.in.query.HubFindQuery;
import com.logistic.hub.application.port.out.client.GpsInternalPort;
import com.logistic.hub.application.port.out.persistence.RoutePersistencePort;
import com.logistic.hub.domain.Hub;
import com.logistic.hub.domain.Route;
import com.logistic.hub.domain.exception.HubNotFoundException;
import com.logistic.hub.domain.exception.HubSameSelectionException;
import com.logistic.hub.domain.exception.RouteAlreadyDeletedException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RouteService implements RouteUseCase {
  private final RoutePersistencePort routePersistencePort;
  private final HubQueryUseCase hubQueryUseCase;
  private final GpsInternalPort gpsInternalPort;

  @Override
  @Caching(evict = {
      @CacheEvict(cacheNames = "routeList", allEntries = true),
      @CacheEvict(cacheNames = "shorestPath", allEntries = true)
  })
  public Route createOrUpdateHubRoute(RouteCreateCommand routeCommand) {

    Hub departHub = hubQueryUseCase.getHubDetails(new HubFindQuery(routeCommand.departHubId()));
    Hub arrivalHub = hubQueryUseCase.getHubDetails(new HubFindQuery(routeCommand.arrivalHubId()));

    String departGps = departHub.getAddress().getLatitude() + "," + departHub.getAddress().getLongitude();
    String arrivalGps = arrivalHub.getAddress().getLatitude() + "," + arrivalHub.getAddress().getLongitude();

    RouteInfoCommand routeInfoCommand = gpsInternalPort.getRouteInfo(departGps, arrivalGps);
    Long departHubId = routeCommand.departHubId();
    Long arrivalHubId = routeCommand.arrivalHubId();
    if (departHubId.equals(arrivalHubId)) {
      throw new HubSameSelectionException("출발 허브와 도착 허브는 같을 수 없습니다");
    }
    if (!hubQueryUseCase.existsHub(departHubId) || !hubQueryUseCase.existsHub(departHubId)) {
      throw new HubNotFoundException("출발 혹은 도착 허브가 존재하지 않습니다");
    }
    Optional<Route> existRoute = routePersistencePort.findByDepartAndArrival(departHubId, arrivalHubId);
    Route route;
    if (existRoute.isPresent()) {
      route = existRoute.get();
      route.update(routeInfoCommand);
    } else {
      route = Route.createRoute(routeCommand, routeInfoCommand);
    }
    return routePersistencePort.save(route);
  }

  private Route getOrElseThrow(Long routeId) {
    return routePersistencePort.findById(routeId);
  }

  @Override
  @Caching(evict = {
      @CacheEvict(cacheNames = "routeList", allEntries = true),
      @CacheEvict(cacheNames = "shorestPath", allEntries = true)
  })
  public void deleteHubRoute(RouteDeleteCommand command) {
    Route route = getOrElseThrow(command.routeId());
    isDeleted(route);
    routePersistencePort.delete(route, command.passport().getUserInfo().getUserId());
  }

  @Override
  public void deleteHubRouteByHubId(RouteDeleteByHubIdCommand command) {
    routePersistencePort.deleteByHubId(command.hubId(), command.passport().getUserInfo().getUserId());

  }

  private void isDeleted(Route route) {
    if (route.getIsDeleted()) {
      throw new RouteAlreadyDeletedException("이미 삭제된 허브입니다.");
    }
  }

}