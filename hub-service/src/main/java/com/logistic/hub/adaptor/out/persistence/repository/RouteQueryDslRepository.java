package com.logistic.hub.adaptor.out.persistence.repository;

import com.logistic.hub.adaptor.in.web.response.RouteHistoryResponse;
import com.logistic.hub.adaptor.out.persistence.entity.QHubEntity;
import com.logistic.hub.adaptor.out.persistence.entity.QRouteEntity;
import com.querydsl.core.types.Projections;
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
}
