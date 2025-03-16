package com.logistic.hub.adaptor.out.persistence;

import com.logistic.common.annotation.PersistenceAdapter;
import com.logistic.hub.adaptor.out.persistence.mapper.RoutePersistenceMapper;
import com.logistic.hub.application.port.out.RoutePersistencePort;
import com.logistic.hub.domain.Route;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class RoutePersistenceAdaptor implements RoutePersistencePort {
  private final RouteJpaRepository routeJpaRepository;
  private final RoutePersistenceMapper routePersistenceMapper;

  @Override
  public Optional<Route> save(Route route) {
    RouteEntity routeEntity = routeJpaRepository.save(routePersistenceMapper.toEntity(route));
    return Optional.of(routePersistenceMapper.toDomain(routeEntity));
  }
}
