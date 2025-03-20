package com.logistic.hub.adapter.out.persistence;

import com.logistic.common.annotation.Adapter;
import com.logistic.hub.adapter.in.web.response.RouteHistoryResponse;
import com.logistic.hub.adapter.out.persistence.mapper.RoutePersistenceMapper;
import com.logistic.hub.adapter.out.persistence.model.RouteEntity;
import com.logistic.hub.adapter.out.persistence.repository.RouteJpaRepository;
import com.logistic.hub.adapter.out.persistence.repository.RouteQueryDslRepository;
import com.logistic.hub.application.port.out.persistence.RoutePersistencePort;
import com.logistic.hub.domain.Route;
import com.logistic.hub.domain.exception.RouteNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Adapter
@RequiredArgsConstructor
public class RoutePersistenceAdaptor implements RoutePersistencePort {
  private final RouteJpaRepository routeJpaRepository;
  private final RoutePersistenceMapper routePersistenceMapper;
  private final RouteQueryDslRepository routeQueryDslRepository;

  @Override
  public Route save(Route route) {
    RouteEntity routeEntity = routeJpaRepository.save(routePersistenceMapper.toEntity(route));
    return routePersistenceMapper.toDomain(routeEntity);
  }

  @Override
  public Page<RouteHistoryResponse> findAllBySearch(String search, Pageable pageable) {
    return routeQueryDslRepository.findAllBySearch(search, pageable);
  }

  @Override
  public Optional<Route> findByDepartAndArrival(Long departHubId, Long arrivalHubId) {
    Optional<RouteEntity> routeEntity = routeJpaRepository.findBydepartHubIdAndArrivalHubIdAndIsDeletedFalse(
        departHubId, arrivalHubId);

    return routeEntity.map(routePersistenceMapper::toDomain);
  }

  @Override
  public List<Route> findAll() {
    List<RouteEntity> all = routeJpaRepository.findAllByIsDeletedFalse();
    List<Route> routeList = all.stream().map(routePersistenceMapper::toDomain).toList();

    return routeList;
  }


  @Override
  public Route findById(Long hubRouteId) {
    RouteEntity routeEntity = routeJpaRepository.findById(hubRouteId)
        .orElseThrow(() -> new RouteNotFoundException("존재하지 않는 경로입니다"));

    return routePersistenceMapper.toDomain(routeEntity);
  }

  @Override
  public void delete(Route route) {
    RouteEntity routeEntity = routeJpaRepository.findById(route.getId())
        .orElseThrow(() -> new RouteNotFoundException("존재하지 않는 경로입니다"));

    routeEntity.delete(true, "test"); //임시
  }


}
