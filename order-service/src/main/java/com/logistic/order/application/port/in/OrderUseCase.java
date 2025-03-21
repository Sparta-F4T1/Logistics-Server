package com.logistic.order.application.port.in;

import com.logistic.order.application.port.in.command.CreateOrderCommand;
import com.logistic.order.domain.Order;
import com.logistic.order.domain.OrderStatus;

public interface OrderUseCase {
  Order createOrder(CreateOrderCommand toCreateCommand);

  Order updateOrder(Long orderId, OrderStatus status);

  void deleteOrder(Long orderId, String userId);

  Order findOrder(Long orderId);
}
