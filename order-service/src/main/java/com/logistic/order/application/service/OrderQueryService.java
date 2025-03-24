package com.logistic.order.application.service;

import com.logistic.order.application.port.OrderPersistencePort;
import com.logistic.order.application.port.in.OrderQueryUseCase;
import com.logistic.order.application.port.in.query.SearchOrderQuery;
import com.logistic.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService implements OrderQueryUseCase {
  private final OrderPersistencePort orderPersistencePort;

  @Override
  public Page<Order> search(SearchOrderQuery query) {
    return orderPersistencePort.search(query);
  }
}
