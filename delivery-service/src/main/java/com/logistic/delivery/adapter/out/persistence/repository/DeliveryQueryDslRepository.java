package com.logistic.delivery.adapter.out.persistence.repository;

import com.logistic.delivery.adapter.out.persistence.DeliveryEntity;
import com.logistic.delivery.adapter.out.persistence.QDeliveryEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryQueryDslRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final QDeliveryEntity deliveryEntity = QDeliveryEntity.deliveryEntity;

  public Optional<DeliveryEntity> findDelivery(Long id) {
    return Optional.ofNullable(
        jpaQueryFactory
            .selectFrom(deliveryEntity)
            .where(deliveryEntity.id.eq(id))
            .fetchOne()
    );
  }

  public Page<DeliveryEntity> searchDelivery(Optional<Long> orderId,
                                             Optional<Long> departCompanyId,
                                             Optional<Long> arrivalCompanyId,
                                             Optional<String> driverId,
                                             Pageable pageable)
  {
    QueryResults<DeliveryEntity> results = jpaQueryFactory
        .selectFrom(deliveryEntity)
        .where(
            orderIdFilter(orderId),
            departCompanyFilter(departCompanyId),
            arrivalCompanyFilter(arrivalCompanyId),
            driverFilter(driverId)
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(pageable.getSort().stream()
            .map(order -> order.isAscending() ? deliveryEntity.id.asc() : deliveryEntity.id.desc())
            .toArray(OrderSpecifier[]::new))
        .fetchResults();

    return new PageImpl<>(results.getResults(), pageable, results.getTotal());
  }

  private BooleanExpression orderIdFilter(Optional<Long> orderId) {
    return orderId.map(deliveryEntity.orderId::eq).orElse(null);
  }

  private BooleanExpression departCompanyFilter(Optional<Long> departCompanyId) {
    return departCompanyId.map(deliveryEntity.departCompanyId::eq).orElse(null);
  }

  private BooleanExpression arrivalCompanyFilter(Optional<Long> arrivalCompanyId) {
    return arrivalCompanyId.map(deliveryEntity.arrivalCompanyId::eq).orElse(null);
  }

  private BooleanExpression driverFilter(Optional<String> driverId) {
    return driverId.map(deliveryEntity.driverId::eq).orElse(null);
  }


}
