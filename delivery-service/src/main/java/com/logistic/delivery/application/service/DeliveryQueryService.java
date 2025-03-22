package com.logistic.delivery.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.delivery.application.port.in.DeliveryQueryUseCase;
import com.logistic.delivery.application.port.in.query.DeliveryFindQuery;
import com.logistic.delivery.application.port.in.query.DeliverySearchQuery;
import com.logistic.delivery.application.port.out.DeliveryQueryPersistencePort;
import com.logistic.delivery.domain.view.DeliveryView;
import com.querydsl.core.QueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class DeliveryQueryService implements DeliveryQueryUseCase {

  private final DeliveryQueryPersistencePort persistencePort;
  private final QueryFactory queryFactory;

  @Override
  public DeliveryView findDelivery(DeliveryFindQuery query) {
    // todo: 예외 처리
    return persistencePort.findDelivery(query.deliveryId()).orElseThrow(null);
  }

  @Override
  public Page<DeliveryView> SearchDelivery(DeliverySearchQuery query, int page, int size, String sortType) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.ASC, sortType));
    return persistencePort.searchDelivery(
        query.orderId(),
        query.departCompanyId(),
        query.arrivalCompanyId(),
        query.driverId(),
        pageable);
  }
}
