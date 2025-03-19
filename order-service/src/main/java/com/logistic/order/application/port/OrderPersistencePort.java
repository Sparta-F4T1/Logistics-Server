package com.logistic.order.application.port;

import com.logistic.order.application.port.in.query.SearchOrderQuery;
import com.logistic.order.domain.Order;
import java.util.Optional;

public interface OrderPersistencePort {
  Order save(Order order);

  Order findById(Long orderId);

  void delete(Long orderId, String userId);
}
