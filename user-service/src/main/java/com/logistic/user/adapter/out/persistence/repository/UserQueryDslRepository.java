package com.logistic.user.adapter.out.persistence.repository;

import static com.logistic.user.adapter.out.persistence.entity.QUserEntity.userEntity;

import com.logistic.user.adapter.out.persistence.entity.UserEntity;
import com.logistic.user.application.port.in.query.SearchUserQuery;
import com.logistic.user.domain.vo.UserStatus;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserQueryDslRepository {
  private final JPAQueryFactory queryFactory;

  public Page<UserEntity> search(final SearchUserQuery query) {
    Map<String, com.querydsl.core.types.Expression<?>> sortFieldMap = Map.of(
        "userId", userEntity.userId,
        "name", userEntity.name,
        "status", userEntity.status,
        "roleName", userEntity.role.name,
        "createdAt", userEntity.createdAt,
        "updatedAt", userEntity.updatedAt
    );

    Pageable pageable = query.pageable();
    List<BooleanExpression> conditions = new ArrayList<>();

    conditions.add(userEntity.deletedAt.isNull());

    if (StringUtils.hasText(query.role())) {
      conditions.add(roleEq(query.role()));
    }

    if (StringUtils.hasText(query.status())) {
      try {
        UserStatus status = UserStatus.valueOf(query.status().toUpperCase());
        conditions.add(statusEq(status));
      } catch (IllegalArgumentException e) {
        log.warn("유효하지 않은 상태: {}", query.status());
        conditions.add(Expressions.asBoolean(false).isTrue());
      }
    }

    BooleanExpression whereCondition = conditions.stream()
        .filter(Objects::nonNull)
        .reduce(BooleanExpression::and)
        .orElse(null);

    List<UserEntity> content = queryFactory
        .selectFrom(userEntity)
        .where(whereCondition)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(getOrderSpecifier(pageable, sortFieldMap))
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
        .select(userEntity.count())
        .from(userEntity)
        .where(whereCondition);

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private BooleanExpression roleEq(String role) {
    return StringUtils.hasText(role) ? userEntity.role.name.eq(role) : null;
  }

  private BooleanExpression statusEq(UserStatus status) {
    return status != null ? userEntity.status.eq(status) : null;
  }

  private OrderSpecifier[] getOrderSpecifier(Pageable pageable,
                                             Map<String, com.querydsl.core.types.Expression<?>> sortFieldMap) {
    if (!pageable.getSort().isSorted()) {
      return new OrderSpecifier[]{new OrderSpecifier(Order.DESC, userEntity.createdAt)};
    }

    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
    for (Sort.Order sortOrder : pageable.getSort()) {
      Order direction = sortOrder.getDirection().isAscending() ? Order.ASC : Order.DESC;

      com.querydsl.core.types.Expression<?> sortField = sortFieldMap.getOrDefault(
          sortOrder.getProperty().toLowerCase(),
          userEntity.createdAt
      );

      orderSpecifiers.add(new OrderSpecifier(direction, sortField));
    }

    return orderSpecifiers.isEmpty()
        ? new OrderSpecifier[]{new OrderSpecifier(Order.DESC, userEntity.createdAt)}
        : orderSpecifiers.toArray(new OrderSpecifier[0]);
  }
}