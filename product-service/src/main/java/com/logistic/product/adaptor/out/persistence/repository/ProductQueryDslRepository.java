package com.logistic.product.adaptor.out.persistence.repository;

import static com.logistic.product.adaptor.out.persistence.QProductEntity.productEntity;

import com.logistic.product.adaptor.in.web.request.ProductSearchRequest;
import com.logistic.product.adaptor.out.persistence.ProductEntity;
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
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductQueryDslRepository {
  private final JPAQueryFactory queryFactory;

  public Page<ProductEntity> search(final ProductSearchRequest request, final Pageable pageable) {
    final BooleanExpression searchExpression = getSearchCondition(request);

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

  private BooleanExpression getSearchCondition(final ProductSearchRequest request) {
    return productEntity.isDeleted.isFalse()
        .and(isCompanyIdEqual(request.companyId()))
        .and(containsQuery(request.query()));
  }

  private BooleanExpression isCompanyIdEqual(final Long companyId) {
    return (companyId == null) ? null : productEntity.info.companyId.eq(companyId);
  }

  private BooleanExpression containsQuery(final String query) {
    return (query == null) ? null : productEntity.info.name.containsIgnoreCase(query);
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
