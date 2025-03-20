package com.logistic.product.adapter.out.persistence.repository;

import static com.logistic.product.adapter.out.persistence.model.QProductEntity.productEntity;

import com.logistic.product.adapter.out.persistence.model.ProductEntity;
import com.logistic.product.application.port.in.query.SearchProductQuery;
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
public class ProductQueryDslRepository {
  private final JPAQueryFactory queryFactory;

  public Page<ProductEntity> search(final SearchProductQuery query) {
    final Long companyId = query.companyId();
    final String name = query.name();
    final Pageable pageable = query.pageable();
    final BooleanExpression searchExpression = getSearchCondition(companyId, name);

    final List<ProductEntity> contents = fetchContents(pageable, searchExpression);

    long total = fetchTotalCount(searchExpression);

    return new PageImpl<>(contents, pageable, total);
  }

  private List<ProductEntity> fetchContents(final Pageable pageable, final BooleanExpression searchExpression) {
    return queryFactory
        .selectFrom(productEntity)
        .where(searchExpression)
        .orderBy(getOrderSpecifiers(pageable))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private BooleanExpression getSearchCondition(final Long companyId, final String name) {
    return productEntity.isDeleted.isFalse()
        .and(isCompanyIdEqual(companyId))
        .and(containsName(name));
  }

  private BooleanExpression isCompanyIdEqual(final Long companyId) {
    return (companyId == null) ? null : productEntity.info.companyId.eq(companyId);
  }

  private BooleanExpression containsName(final String name) {
    return (name == null) ? null : productEntity.info.name.containsIgnoreCase(name);
  }

  private OrderSpecifier<?>[] getOrderSpecifiers(final Pageable pageable) {
    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

    final Sort sort = pageable.getSort();
    sort.forEach(order -> {
      final String sortBy = order.getProperty();
      final Order direction = order.isAscending() ? Order.ASC : Order.DESC;
      if ("createdAt".equals(sortBy)) {
        orderSpecifiers.add(new OrderSpecifier<>(direction, productEntity.createdAt));
      }
      if ("updatedAt".equals(sortBy)) {
        orderSpecifiers.add(new OrderSpecifier<>(direction, productEntity.updatedAt));
      }
    });

    return orderSpecifiers.toArray(new OrderSpecifier[0]);
  }

  private Long fetchTotalCount(final BooleanExpression expression) {
    final Long total = queryFactory
        .select(productEntity.count())
        .from(productEntity)
        .where(expression)
        .fetchOne();
    return total == null ? 0L : total;
  }
}
