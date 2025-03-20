package com.logistic.order.adapter.out.persistence.repository;

import static com.logistic.order.adapter.out.persistence.QOrderEntity.orderEntity;

import com.logistic.order.adapter.out.persistence.OrderEntity;
import com.logistic.order.application.port.in.query.SearchOrderQuery;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderQueryDslRepository {
  private final JPAQueryFactory queryFactory;

  public Page<OrderEntity> search(SearchOrderQuery query) {
    Long sellerId = query.sellerId();
    Long buyerId = query.buyerId();
    LocalDateTime dateStart = query.dateStart();
    LocalDateTime dateEnd = query.dateEnd();
    Pageable pageable = query.pageable();

    BooleanExpression expression = getSearchConditions(dateStart, dateEnd, sellerId, buyerId);

    List<OrderEntity> entities = queryFactory
        .selectFrom(orderEntity)
        .where(expression)
        .orderBy(getAllOrderSpecifiers(pageable).toArray(new OrderSpecifier[]{}))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    return new PageImpl<>(entities, pageable, getTotalCount(expression));
  }

  private long getTotalCount(BooleanExpression expression) {
    final Long total = queryFactory
        .select(orderEntity.count())
        .from(orderEntity)
        .where(expression)
        .fetchOne();
    return total == null ? 0L : total;
  }

  private BooleanExpression getSearchConditions(LocalDateTime dateStart, LocalDateTime dateEnd, Long sellerId,
                                                Long buyerId) {
    return orderEntity.isDeleted.isFalse()
        .and(dateBetween(dateStart, dateEnd))
        .and(companiesEqual(sellerId, buyerId));
  }

  private BooleanExpression dateBetween(LocalDateTime dateStart, LocalDateTime dateEnd) {
    if (dateStart != null && dateEnd != null) {
      return orderEntity.createdAt.between(dateStart, dateEnd);
    }
    if (dateStart != null) {
      return orderEntity.createdAt.after(dateStart);
    }
    if (dateEnd != null) {
      return orderEntity.createdAt.before(dateEnd);
    }

    return null;
  }

  private BooleanExpression companiesEqual(Long sellerId, Long buyerId) {
    if (sellerId != null) {
      return orderEntity.sellerId.eq(sellerId);
    }

    if (buyerId != null) {
      return orderEntity.buyerId.eq(buyerId);
    }

    return null;
  }

  private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
    Sort sort = pageable.getSort();
    Order direction = sort.getOrderFor("createdAt").isAscending() ? Order.ASC : Order.DESC;
    return List.of(new OrderSpecifier<>(direction, orderEntity.createdAt));
  }

}
