package com.logistic.driver.adapter.out.persistence.repository;

import static com.logistic.driver.adapter.out.persistence.model.entity.QDriverViewEntity.driverViewEntity;

import com.logistic.driver.adapter.out.persistence.model.entity.DriverViewEntity;
import com.logistic.driver.application.port.in.query.SearchDriverQuery;
import com.logistic.driver.domain.model.vo.DriverStatus;
import com.logistic.driver.domain.model.vo.DriverType;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DriverViewQueryDslRepository {
  private final JPAQueryFactory queryFactory;

  public Page<DriverViewEntity> search(final SearchDriverQuery query) {
    final Long hubId = query.hubId();
    final DriverType type = query.type();
    final DriverStatus status = query.status();
    final Pageable pageable = query.pageable();

    final BooleanExpression searchExpression = getSearchCondition(hubId, type, status);

    final List<DriverViewEntity> contents = fetchContents(pageable, searchExpression);

    long total = fetchTotalCount(searchExpression);

    return new PageImpl<>(contents, pageable, total);
  }

  private List<DriverViewEntity> fetchContents(final Pageable pageable, final BooleanExpression searchExpression) {
    return queryFactory
        .selectFrom(driverViewEntity)
        .where(searchExpression)
        .orderBy(getOrderSpecifiers(pageable))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private BooleanExpression getSearchCondition(final Long hubId, final DriverType type, final DriverStatus status) {
    return driverViewEntity.isDeleted.isFalse()
        .and(isHubIdEqual(hubId))
        .and(isTypeEqual(type))
        .and(isStatusEqual(status));
  }

  private BooleanExpression isHubIdEqual(final Long hubId) {
    return (hubId == null) ? null : driverViewEntity.hub.hubId.eq(hubId);
  }

  private BooleanExpression isTypeEqual(final DriverType type) {
    return (type == null) ? null : driverViewEntity.type.eq(type);
  }

  private BooleanExpression isStatusEqual(final DriverStatus status) {
    return (status == null) ? null : driverViewEntity.status.eq(status);
  }

  private OrderSpecifier<?>[] getOrderSpecifiers(final Pageable pageable) {
    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

    final Sort sort = pageable.getSort();
    sort.forEach(order -> {
      final String sortBy = order.getProperty();
      final Order direction = order.isAscending() ? Order.ASC : Order.DESC;
      if ("createdAt".equals(sortBy)) {
        orderSpecifiers.add(new OrderSpecifier<>(direction, driverViewEntity.createdAt));
      }
      if ("updatedAt".equals(sortBy)) {
        orderSpecifiers.add(new OrderSpecifier<>(direction, driverViewEntity.updatedAt));
      }
    });

    return orderSpecifiers.toArray(new OrderSpecifier[0]);
  }

  private Long fetchTotalCount(final BooleanExpression expression) {
    final Long total = queryFactory
        .select(driverViewEntity.count())
        .from(driverViewEntity)
        .where(expression)
        .fetchOne();
    return total == null ? 0L : total;
  }
}
