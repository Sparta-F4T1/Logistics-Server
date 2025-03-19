package com.logistic.driver.adapter.out.persistence.repository;

import static com.logistic.driver.adapter.out.persistence.model.entity.QDriverEntity.driverEntity;

import com.logistic.driver.adapter.out.persistence.model.entity.DriverEntity;
import com.logistic.driver.application.port.in.query.SearchDriverQuery;
import com.logistic.driver.domain.DriverType;
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
    final Long departHubId = query.departHubId();
    final Long arrivalHubId = query.arrivalHubId();
    final DriverType type = query.type();
    final Pageable pageable = query.pageable();

    final BooleanExpression searchExpression = getSearchCondition(departHubId, arrivalHubId, type);

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

  private BooleanExpression getSearchCondition(final Long departHubId, final Long arrivalHubId, final DriverType type) {
    return driverEntity.isDeleted.isFalse()
        .and(isDepartHubIdEqual(departHubId))
        .and(isArrivalHubIdEqual(arrivalHubId))
        .and(isTypeEqual(type));
  }

  private BooleanExpression isDepartHubIdEqual(final Long departHubId) {
    return (departHubId == null) ? null : driverEntity.departHubId.eq(departHubId);
  }

  private BooleanExpression isArrivalHubIdEqual(final Long arrivalHubId) {
    return (arrivalHubId == null) ? null : driverEntity.arrivalHubId.eq(arrivalHubId);
  }

  private BooleanExpression isTypeEqual(final DriverType type) {
    return (type == null) ? null : driverEntity.type.eq(type);
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
