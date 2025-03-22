package com.logistic.driver.adapter.out.persistence.repository;

import static com.logistic.driver.adapter.out.persistence.model.entity.QDriverEntity.driverEntity;

import com.logistic.driver.adapter.out.persistence.model.entity.DriverEntity;
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
public class DriverQueryDslRepository {
  private final JPAQueryFactory queryFactory;

  public Page<DriverEntity> search(final SearchDriverQuery query) {
    final Long hubId = query.hubId();
    final DriverType type = query.type();
    final DriverStatus status = query.status();
    final Pageable pageable = query.pageable();

    final BooleanExpression searchExpression = getSearchCondition(hubId, type, status);

    final List<DriverEntity> contents = fetchContents(pageable, searchExpression);

    long total = fetchTotalCount(searchExpression);

    return new PageImpl<>(contents, pageable, total);
  }

  private List<DriverEntity> fetchContents(final Pageable pageable, final BooleanExpression searchExpression) {
    return queryFactory
        .selectFrom(driverEntity)
        .where(searchExpression)
        .orderBy(getOrderSpecifiers(pageable))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private BooleanExpression getSearchCondition(final Long hubId, final DriverType type, final DriverStatus status) {
    return driverEntity.isDeleted.isFalse()
        .and(isHubIdEqual(hubId))
        .and(isTypeEqual(type))
        .and(isStatusEqual(status));
  }

  private BooleanExpression isHubIdEqual(final Long hubId) {
    return (hubId == null) ? null : driverEntity.hubId.eq(hubId);
  }

  private BooleanExpression isTypeEqual(final DriverType type) {
    return (type == null) ? null : driverEntity.type.eq(type);
  }

  private BooleanExpression isStatusEqual(final DriverStatus status) {
    return (status == null) ? null : driverEntity.status.eq(status);
  }

  private OrderSpecifier<?>[] getOrderSpecifiers(final Pageable pageable) {
    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

    final Sort sort = pageable.getSort();
    sort.forEach(order -> {
      final String sortBy = order.getProperty();
      final Order direction = order.isAscending() ? Order.ASC : Order.DESC;
      if ("createdAt".equals(sortBy)) {
        orderSpecifiers.add(new OrderSpecifier<>(direction, driverEntity.createdAt));
      }
      if ("updatedAt".equals(sortBy)) {
        orderSpecifiers.add(new OrderSpecifier<>(direction, driverEntity.updatedAt));
      }
    });

    return orderSpecifiers.toArray(new OrderSpecifier[0]);
  }

  private Long fetchTotalCount(final BooleanExpression expression) {
    final Long total = queryFactory
        .select(driverEntity.count())
        .from(driverEntity)
        .where(expression)
        .fetchOne();
    return total == null ? 0L : total;
  }
}
