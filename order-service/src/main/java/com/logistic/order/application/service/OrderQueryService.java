package com.logistic.order.application.service;

import com.logistic.order.application.port.OrderPersistencePort;
import com.logistic.order.application.port.in.OrderQueryUseCase;
import com.logistic.order.application.port.in.query.SearchOrderQuery;
import com.logistic.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService implements OrderQueryUseCase {
  private final OrderPersistencePort orderPersistencePort;

  @Override
  public PagedModel<Order> search(SearchOrderQuery query) {
    return new PagedModel<>(orderPersistencePort.search(query));
  }
}
