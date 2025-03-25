package com.logistic.order.application.port.out;

import com.logistic.order.domain.Order;

public interface MessagePort {
  void sendCreateOrder(Order order, String slackEmail);
}
