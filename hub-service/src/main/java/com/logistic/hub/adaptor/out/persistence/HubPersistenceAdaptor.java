package com.logistic.hub.adaptor.out.persistence;

import com.logistic.common.annotation.PersistenceAdapter;
import com.logistic.hub.adaptor.in.web.response.HubHistoryResponse;
import com.logistic.hub.adaptor.out.persistence.mapper.HubPersistenceMapper;
import com.logistic.hub.application.port.out.HubPersistencePort;
import com.logistic.hub.domain.Hub;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
public class HubPersistenceAdaptor implements HubPersistencePort {
  private final HubJpaRepository hubJpaRepository;
  private final JPAQueryFactory jpaQueryFactory;
  private final HubPersistenceMapper hubPersistenceMapper;
  QHubEntity hubEntity = QHubEntity.hubEntity;

  @Override
  public Optional<Hub> save(Hub hub) {
    HubEntity hubEntity = hubJpaRepository.save(hubPersistenceMapper.toEntity(hub));
    return Optional.of(hubPersistenceMapper.toDomain(hubEntity));
  }

  @Override
  public Page<HubHistoryResponse> findAllBySearch(String search, Pageable pageable) {

    List<HubHistoryResponse> query = jpaQueryFactory.select(Projections.constructor(HubHistoryResponse.class,
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

  @Override
  public Optional<Hub> findById(Long hubId) {
    Optional<HubEntity> hubEntity = hubJpaRepository.findById(hubId);

    return hubEntity.map(hubPersistenceMapper::toDomain);
  }

  @Override
  public void delete(Hub hub) {
    Optional<HubEntity> hubEntity = hubJpaRepository.findById(hub.getId());

    hubEntity.ifPresent(entity -> entity.delete(true, "test")); //임시
  }

  private BooleanExpression containsSearch(String search) {
    return (search != null && !search.isEmpty()) ? hubEntity.hubName.contains(search) : null;
  }

}
