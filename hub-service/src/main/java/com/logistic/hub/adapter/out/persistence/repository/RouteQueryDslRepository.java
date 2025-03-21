package com.logistic.hub.adapter.out.persistence.repository;

import com.logistic.hub.adapter.out.persistence.model.QHubEntity;
import com.logistic.hub.adapter.out.persistence.model.QRouteEntity;
import com.logistic.hub.application.service.dto.RouteHistoryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RouteQueryDslRepository {
  private final JPAQueryFactory jpaQueryFactory;
  QRouteEntity routeEntity = QRouteEntity.routeEntity;
  QHubEntity departHub = new QHubEntity("departHub");
  QHubEntity arrivalHub = new QHubEntity("arrivalHub");

  public Page<RouteHistoryDto> findAllBySearch(String searchType, String search, Pageable pageable) {
    List<RouteHistoryDto> query = jpaQueryFactory.select(Projections.constructor(RouteHistoryDto.class,
            routeEntity.id,
            departHub.hubName,
            arrivalHub.hubName))
        .from(routeEntity)
        .where(
            routeEntity.isDeleted.eq(false),
            containsSearch(searchType, search)
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
            routeEntity.isDeleted.eq(false),
            containsSearch(searchType, search)
        );

    return PageableExecutionUtils.getPage(query, pageable, () -> totalCount.fetchOne());
  }

  private BooleanExpression containsSearch(String searchType, String search) {
    if (searchType.equals("departHubName")) {
      return (search != null && !search.isEmpty()) ? departHub.hubName.contains(search) : null;
    }
    if (searchType.equals("arrivalHubName")) {
      return (search != null && !search.isEmpty()) ? arrivalHub.hubName.contains(search) : null;
    }
    return null;
  }
}
