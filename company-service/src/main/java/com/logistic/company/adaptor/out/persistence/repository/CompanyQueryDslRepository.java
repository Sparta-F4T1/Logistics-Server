package com.logistic.company.adaptor.out.persistence.repository;

import static com.logistic.company.adaptor.out.persistence.QCompanyEntity.companyEntity;

import com.logistic.company.adaptor.out.persistence.CompanyEntity;
import com.logistic.company.application.port.in.query.CompanySearchQuery;
import com.logistic.company.domain.CompanyType;
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
public class CompanyQueryDslRepository {
  private final JPAQueryFactory queryFactory;

  public Page<CompanyEntity> search(final CompanySearchQuery query) {
    final Long hubId = query.hubId();
    final CompanyType type = query.type();
    final String name = query.name();
    final Pageable pageable = query.pageable();

    final BooleanExpression searchExpression = getSearchCondition(hubId, type, name);

    final List<CompanyEntity> contents = fetchContents(pageable, searchExpression);

    long total = fetchTotalCount(searchExpression);

    return new PageImpl<>(contents, pageable, total);
  }

  private List<CompanyEntity> fetchContents(final Pageable pageable, final BooleanExpression searchExpression) {
    return queryFactory
        .selectFrom(companyEntity)
        .where(searchExpression)
        .orderBy(getOrderSpecifiers(pageable))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private BooleanExpression getSearchCondition(final Long hubId, final CompanyType type, final String name) {
    return companyEntity.isDeleted.isFalse()
        .and(isHubIdEqual(hubId))
        .and(isTypeEqual(type))
        .and(containsName(name));
  }

  private BooleanExpression isHubIdEqual(final Long hubId) {
    return (hubId == null) ? null : companyEntity.hubId.eq(hubId);
  }

  private BooleanExpression containsName(final String name) {
    return (name == null) ? null : companyEntity.name.containsIgnoreCase(name);
  }

  private BooleanExpression isTypeEqual(final CompanyType type) {
    return (type == null) ? null : companyEntity.type.eq(type);
  }

  private OrderSpecifier<?>[] getOrderSpecifiers(final Pageable pageable) {
    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

    final Sort sort = pageable.getSort();
    sort.forEach(order -> {
      final String sortBy = order.getProperty();
      final Order direction = order.isAscending() ? Order.ASC : Order.DESC;
      if ("createdAt".equals(sortBy)) {
        orderSpecifiers.add(new OrderSpecifier<>(direction, companyEntity.createdAt));
      }
      if ("updatedAt".equals(sortBy)) {
        orderSpecifiers.add(new OrderSpecifier<>(direction, companyEntity.updatedAt));
      }
    });

    return orderSpecifiers.toArray(new OrderSpecifier[0]);
  }

  private Long fetchTotalCount(final BooleanExpression expression) {
    final Long total = queryFactory
        .select(companyEntity.count())
        .from(companyEntity)
        .where(expression)
        .fetchOne();
    return total == null ? 0L : total;
  }
}
