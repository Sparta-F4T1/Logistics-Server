package com.logistic.order.application.port.in;

import com.logistic.order.application.port.in.command.OrderCreateCommand;
import com.logistic.order.domain.Order;

public interface OrderUseCase {
  public Order createOrder(OrderCreateCommand toCreateCommand);
}
