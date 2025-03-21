package com.logistic.hub.adapter.out.persistence.repository;

import com.logistic.hub.adapter.out.persistence.model.QHubEntity;
import com.logistic.hub.application.service.dto.HubHistoryDto;
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
public class HubQueryDslRepository {
  private final JPAQueryFactory jpaQueryFactory;
  QHubEntity hubEntity = QHubEntity.hubEntity;

  public Page<HubHistoryDto> findAllBySearch(String search, Pageable pageable) {

    List<HubHistoryDto> query = jpaQueryFactory.select(Projections.constructor(HubHistoryDto.class,
            hubEntity.id,
            hubEntity.hubType,
            hubEntity.hubName,
            hubEntity.address.road,
            hubEntity.address.jibun))
        .from(hubEntity)
        .where(
            hubEntity.isDeleted.eq(false),
            containsSearch(search)
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(hubEntity.hubName.asc())
        .fetch();

    JPAQuery<Long> totalCount = jpaQueryFactory.select(hubEntity.count())
        .from(hubEntity)
        .where(
            hubEntity.isDeleted.eq(false),
            containsSearch(search)
        );

    return PageableExecutionUtils.getPage(query, pageable, () -> totalCount.fetchOne());
  }


  private BooleanExpression containsSearch(String search) {
    return (search != null && !search.isEmpty()) ? hubEntity.hubName.contains(search) : null;
  }

}
