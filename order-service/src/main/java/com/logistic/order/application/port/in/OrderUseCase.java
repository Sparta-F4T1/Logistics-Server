package com.logistic.order.application.port.in;

import com.logistic.common.passport.model.UserInfo;
import com.logistic.order.application.port.in.command.CreateOrderCommand;
import com.logistic.order.domain.Order;
import com.logistic.order.domain.OrderStatus;

public interface OrderUseCase {
  Order createOrder(CreateOrderCommand toCreateCommand);

  Order updateOrder(Long orderId, OrderStatus status, UserInfo userInfo);

  void deleteOrder(Long orderId, UserInfo userInfo);

  Order findOrder(Long orderId, UserInfo userInfo);
}
