package com.logistic.order.application.port;

import com.logistic.order.domain.Order;

public interface OrderPersistencePort {
  Order save(Order order);
}
