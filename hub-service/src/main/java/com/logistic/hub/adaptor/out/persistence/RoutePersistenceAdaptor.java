package com.logistic.hub.adaptor.out.persistence;

import com.logistic.common.annotation.PersistenceAdapter;
import com.logistic.hub.adaptor.in.web.response.RouteHistoryResponse;
import com.logistic.hub.adaptor.out.persistence.mapper.RoutePersistenceMapper;
import com.logistic.hub.application.port.out.RoutePersistencePort;
import com.logistic.hub.domain.Route;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@PersistenceAdapter
@RequiredArgsConstructor
public class RoutePersistenceAdaptor implements RoutePersistencePort {
  private final RouteJpaRepository routeJpaRepository;
  private final RoutePersistenceMapper routePersistenceMapper;
  private final JPAQueryFactory jpaQueryFactory;
  QRouteEntity routeEntity = QRouteEntity.routeEntity;
  QHubEntity departHub = new QHubEntity("departHub");
  QHubEntity arrivalHub = new QHubEntity("arrivalHub");

  @Override
  public Optional<Route> save(Route route) {
    RouteEntity routeEntity = routeJpaRepository.save(routePersistenceMapper.toEntity(route));
    return Optional.of(routePersistenceMapper.toDomain(routeEntity));
  }

  @Override
  public Page<RouteHistoryResponse> findAllBySearch(String search, Pageable pageable) {
    List<RouteHistoryResponse> query = jpaQueryFactory.select(Projections.constructor(RouteHistoryResponse.class,
            routeEntity.id,
            departHub.hubName,
            arrivalHub.hubName))
        .from(routeEntity)
        .where(
            routeEntity.isDeleted.eq(false)
            // containsSearch(search)
        )
        .join(departHub).on(routeEntity.departHubId.eq(departHub.id))
        .join(arrivalHub).on(routeEntity.arrivalHubId.eq(arrivalHub.id))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(routeEntity.updatedAt.desc())
        .fetch();

    JPAQuery<Long> totalCount = jpaQueryFactory.select(routeEntity.count())
        .from(routeEntity)
        .where(
            routeEntity.isDeleted.eq(false)
            //containsSearch(search)
        );

    return PageableExecutionUtils.getPage(query, pageable, () -> totalCount.fetchOne());
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
  public Optional<Route> findById(Long hubRouteId) {
    Optional<RouteEntity> routeEntity = routeJpaRepository.findById(hubRouteId);

    return routeEntity.map(routePersistenceMapper::toDomain);
  }

  @Override
  public void delete(Route route) {
    Optional<RouteEntity> routeEntity = routeJpaRepository.findById(route.getId());

    routeEntity.ifPresent(entity -> entity.delete(true, "test")); //임시
  }


}
